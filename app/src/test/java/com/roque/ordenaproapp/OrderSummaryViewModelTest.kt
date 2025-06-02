package com.roque.ordenaproapp

import app.cash.turbine.test
import com.roque.domain.model.CartItem
import com.roque.domain.model.Order
import com.roque.domain.usecase.cart.ClearCartUseCase
import com.roque.domain.usecase.cart.GetCartUseCase
import com.roque.domain.usecase.order.SaveOrderUseCase
import com.roque.ordenaproapp.ui.screens.orders.summary.OrderSummaryUiState
import com.roque.ordenaproapp.ui.screens.orders.summary.OrderSummaryViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class OrderSummaryViewModelTest {

    // Regla para manejar el Dispatcher.Main en los tests
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks para los casos de uso
    private lateinit var mockSaveOrderUseCase: SaveOrderUseCase
    private lateinit var mockGetCartItemsUseCase: GetCartUseCase
    private lateinit var mockClearCartUseCase: ClearCartUseCase

    // La instancia del ViewModel que vamos a probar
    private lateinit var viewModel: OrderSummaryViewModel

    @Before
    fun setUp() {
        // Inicializamos los mocks
        mockSaveOrderUseCase = mockk()
        mockGetCartItemsUseCase = mockk()
        mockClearCartUseCase = mockk()

        // Creamos la instancia del ViewModel con los mocks
        viewModel = OrderSummaryViewModel(
            saveOrderUseCase = mockSaveOrderUseCase,
            getCartItemsUseCase = mockGetCartItemsUseCase,
            clearCartUseCase = mockClearCartUseCase
        )
    }

    @After
    fun tearDown() {
        // Limpiamos los mocks después de cada test (opcional pero buena práctica)
        unmockkAll()
    }

    @Test
    fun `getCartItems should update cartItems and pricing correctly`() = runTest {
        // Arrange
        val cartItems = listOf(
            CartItem(1,"1", "Item 1", 10.0, "",2),
            CartItem(2,"2", "Item 2", 5.0, "",1, )
        )
        val expectedSubtotal = (10.0 * 2) + (5.0 * 1) // 20 + 5 = 25
        val expectedTaxes = expectedSubtotal * 0.15
        val expectedDeliveryFee = 3.50
        val expectedTotal = expectedSubtotal + expectedTaxes + expectedDeliveryFee

        // Mockeamos la respuesta del caso de uso
        every { mockGetCartItemsUseCase() } returns flowOf(cartItems)

        // Act
        viewModel.getCartItems()

        // Assert
        // Usamos Turbine para probar el StateFlow de pricing
        viewModel.pricing.test {
            val pricingState = awaitItem() // Esperamos la emisión
            assertEquals(expectedSubtotal, pricingState.subtotal, 0.001)
            assertEquals(expectedTaxes, pricingState.taxes, 0.001)
            assertEquals(expectedDeliveryFee, pricingState.deliveryFee, 0.001)
            assertEquals(expectedTotal, pricingState.total, 0.001)
            ensureAllEventsConsumed() // Aseguramos que no haya más emisiones inesperadas
        }
        // También podemos verificar el currentCartItems directamente (aunque es privado, lo probamos por su efecto)
        // Para probarlo directamente necesitaríamos hacerlo visible para tests o confiar en su uso en confirmOrder.
        // Aquí confiamos en que se usa correctamente en calculatePricing que afecta a _pricing.
    }

    @Test
    fun `getCartItems with empty cart should update pricing with zero values except delivery if applicable`() =
        runTest {
            // Arrange
            val emptyCartItems = emptyList<CartItem>()
            every { mockGetCartItemsUseCase() } returns flowOf(emptyCartItems)

            // Act
            viewModel.getCartItems()

            // Assert
            viewModel.pricing.test {
                val pricingState = awaitItem()
                assertEquals(0.0, pricingState.subtotal, 0.001)
                assertEquals(0.0, pricingState.taxes, 0.001)
                assertEquals(
                    0.0,
                    pricingState.deliveryFee,
                    0.001
                ) // O 3.50 si la lógica es que siempre se cobra si se intenta obtener el carrito
                assertEquals(0.0, pricingState.total, 0.001)
                ensureAllEventsConsumed()
            }
        }


    @Test
    fun `confirmOrder with empty cart should emit Error and not call use cases`() = runTest {
        // Arrange
        val customerName = "Test Customer"
        // Aseguramos que el carrito esté vacío (estado inicial o después de clearCart)
        every { mockGetCartItemsUseCase() } returns flowOf(emptyList()) // Asegura que currentCartItems esté vacío
        viewModel.getCartItems() // para inicializar currentCartItems como vacío
        viewModel.pricing.test { awaitItem() } // Consumir la emisión de pricing

        // Act & Assert
        viewModel.uiState.test {
            assertEquals(OrderSummaryUiState.Idle, awaitItem())

            viewModel.confirmOrder(customerName)

            val errorState = awaitItem()
            assertTrue(errorState is OrderSummaryUiState.Error)
            assertEquals("El carrito está vacío", (errorState as OrderSummaryUiState.Error).message)

            // Verificamos que los casos de uso NO fueron llamados
            coVerify(exactly = 0) { mockSaveOrderUseCase(any()) }
            coVerify(exactly = 0) { mockClearCartUseCase() }

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `confirmOrder with blank customer name should emit Error and not call use cases`() =
        runTest {
            // Arrange
            val customerName = "  " // Nombre vacío o en blanco
            val cartItems = listOf(CartItem(1,"1", "Item 1", 10.0, "", 1))
            every { mockGetCartItemsUseCase() } returns flowOf(cartItems)
            viewModel.getCartItems()
            viewModel.pricing.test { awaitItem() }


            // Act & Assert
            viewModel.uiState.test {
                assertEquals(OrderSummaryUiState.Idle, awaitItem())

                viewModel.confirmOrder(customerName)

                val errorState = awaitItem()
                assertTrue(errorState is OrderSummaryUiState.Error)
                assertEquals(
                    "El nombre del cliente no puede estar vacío",
                    (errorState as OrderSummaryUiState.Error).message
                )

                coVerify(exactly = 0) { mockSaveOrderUseCase(any()) }
                coVerify(exactly = 0) { mockClearCartUseCase() }

                cancelAndConsumeRemainingEvents()
            }
        }

}