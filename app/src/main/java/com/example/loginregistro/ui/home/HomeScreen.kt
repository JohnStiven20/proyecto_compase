package com.example.loginregistro.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.loginregistro.R
import com.example.loginregistro.ui.componentes.MenuPizzeria
import com.example.loginregistro.ui.data.modelo.ProductoDTO
import com.example.loginregistro.ui.data.modelo.Tipo
import modelo.Size


@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val productos by homeViewModel.productosLiveData.observeAsState(mutableListOf())
    val contadorCarrito = homeViewModel.contadorCarritoLiveData.observeAsState(0).value ?: 0

    val pizzas = productos.filter { it.tipo == Tipo.PIZZA }
    val pastas = productos.filter { it.tipo == Tipo.PASTA }
    val bebidas = productos.filter { it.tipo == Tipo.BEBIDA }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.outline),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        item {
            TopBar(contadorCarrito)
        }

        item {
            LogoImage()
        }

        item {
            ProductCarousel(
                productos = pizzas,
                imagenProducto = painterResource(R.drawable.fotopizza),
                tituloSeccion = "Sección Pizza",
                onChangeCarrito = {homeViewModel.onChageContadorCarrito(contadorCarrito + it)},
                onAddCar = {cantidad, size, producto ->
                    homeViewModel.onAddCar(cantidad, size, producto)
                }


            )
        }

        item {
            ProductCarousel(
                productos = pastas,
                imagenProducto = painterResource(R.drawable.fotopasta),
                tituloSeccion = "Sección Pasta",
                onChangeCarrito = {homeViewModel.onChageContadorCarrito(contadorCarrito + it)},
                onAddCar = {cantidad, size, producto ->
                    homeViewModel.onAddCar(cantidad, size, producto)
                })
        }

        item {
            ProductCarousel(
                productos = bebidas,
                imagenProducto = painterResource(R.drawable.fotobebida),
                tituloSeccion = "Sección Bebida",
                onChangeCarrito = {homeViewModel.onChageContadorCarrito(contadorCarrito + it)},
                onAddCar = {cantidad, size, producto ->
                    homeViewModel.onAddCar(cantidad, size, producto)
                }
            )
        }
    }
}

@Composable
fun ProductCarousel(
    productos: List<ProductoDTO>,
    imagenProducto: Painter = painterResource(R.drawable.fotopizza),
    tituloSeccion: String = "",
    onChangeCarrito: (Int) -> Unit,
    onAddCar: (Int, Size, ProductoDTO) -> Unit
) {

    Text(text = tituloSeccion)

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow {
        items(productos) { producto ->
            ProductCard(
                producto = producto,
                imagenProducto = imagenProducto,
                onChangeContadorCarrito = {onChangeCarrito(it)},
                onAddCar = onAddCar
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun ProductCard(
    imagenProducto: Painter = painterResource(R.drawable.fotopizza),
    producto: ProductoDTO = ProductoDTO(),
    onChangeContadorCarrito: (Int) -> Unit,
    onAddCar: (Int, Size, ProductoDTO) -> Unit
){

    val ingredientes: String = producto.listaIngredienteDTO.joinToString { it.nombre }
    var cantidad by rememberSaveable { mutableIntStateOf(1) }
    var sizeProducto by rememberSaveable { mutableStateOf(Size.GRANDE) }
    var botonHabilitado by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .width(380.dp)
            .height(180.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = imagenProducto,
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = ingredientes,
                        maxLines = 2,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${producto.precio} €",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TextButton(
                        onClick = {
                            onChangeContadorCarrito(cantidad)
                            onAddCar(cantidad ,sizeProducto,producto)
                        },
                        enabled = botonHabilitado
                    ) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = {
                        if (cantidad != 1) {
                            cantidad--
                        }
                    }) {
                        Text("-")
                    }
                    Text(
                        text = cantidad.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    TextButton(onClick = {
                        cantidad++
                    }) {
                        Text("+")
                    }
                }
                sizeProducto = MenuPizzeria()

                botonHabilitado = sizeProducto != Size.NADA
            }
        }
    }

}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(R.drawable.logopizza),
        contentDescription = "Imagen del logo",
        modifier = Modifier
            .padding(30.dp)
            .size(width = 200.dp, height = 200.dp)
            .clip(shape = RoundedCornerShape(percent = 100))
            .border(
                3.dp,
                MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(percent = 100)
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(contadorCarrito: Int) {
    TopAppBar(
        title = { Text(text = "Pizzería") },
        actions = {
            BadgedBox(
                badge = {}
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito",
                        modifier = Modifier.padding(8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = 15.dp, y = 25.dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.Red),
                        ) {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.offset(y = (-2).dp, x = 0.dp),
                                text = contadorCarrito.toString()
                            )
                        }
                    }
                }
            }
        }
    )
}

