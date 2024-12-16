package com.example.loginregistro.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.loginregistro.R
import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.modelo.Tipo
import com.example.loginregistro.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import modelo.Size


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, navController: NavController) {

    val screens: MutableList<Screen> = mutableListOf(Screen.Home)

    val productos by homeViewModel.productosLiveData.observeAsState(mutableListOf())
    val contadorCarrito = homeViewModel.contadorCarritoLiveData.observeAsState(0).value ?: 0
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Drawer(
                navController = navController,
                drawerState = drawerState,
                scope = scope,
                items = screens
            )
        })
    {
        Scaffold(
            topBar = {
                TopBar(contadorCarrito, drawerState, scope)
            },
            content = { paddingValues ->
                Body(
                    productos = productos,
                    paddingValues = paddingValues,
                    homeViewModel = homeViewModel
                )

            }

        )
    }
}

@Composable
fun Body(
    productos: List<ProductoDTO>,
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel

) {

    val pizzas = productos.filter { it.tipo == Tipo.PIZZA }
    val pastas = productos.filter { it.tipo == Tipo.PASTA }
    val bebidas = productos.filter { it.tipo == Tipo.BEBIDA }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.outline)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        item {
            LogoImage()
        }

        item {
            ProductCarousel(
                productos = pizzas,
                imagenProducto = painterResource(R.drawable.fotopizza),
                tituloSeccion = "Sección Pizza",
                onAddCar = { cantidad, size, producto ->
                    homeViewModel.onAddCar(cantidad, size, producto)
                }


            )
        }

        item {
            ProductCarousel(
                productos = pastas,
                imagenProducto = painterResource(R.drawable.fotopasta),
                tituloSeccion = "Sección Pasta",
                onAddCar = { cantidad, size, producto ->
                    homeViewModel.onAddCar(cantidad, size, producto)
                }
            )
        }

        item {
            ProductCarousel(
                productos = bebidas,
                imagenProducto = painterResource(R.drawable.fotobebida),
                tituloSeccion = "Sección Bebida",
                onAddCar = { cantidad, size, producto ->
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
    onAddCar: (Int, Size, ProductoDTO) -> Unit
) {

    Text(text = tituloSeccion)

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow {
        items(productos) { producto ->
            ProductCard(
                producto = producto,
                imagenProducto = imagenProducto,
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
    onAddCar: (Int, Size, ProductoDTO) -> Unit
) {

    val ingredientes: String = producto.listaIngredienteDTO.joinToString { it.nombre }
    var cantidad by rememberSaveable { mutableIntStateOf(1) }
    var sizeProducto by rememberSaveable { mutableStateOf(Size.GRANDE) }
    var botonHabilitado by rememberSaveable { mutableStateOf(false) }
    var contexto = LocalContext.current


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
                            onAddCar(cantidad, sizeProducto, producto)
                            Toast.makeText(
                                contexto,
                                "Se añadido $cantidad ${producto.tipo.toString().lowercase()}",
                                Toast.LENGTH_SHORT
                            ).show()
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
fun MenuPizzeria(): Size {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var texto by rememberSaveable { mutableStateOf("selecciona tamaño") }
    var tipo by rememberSaveable { mutableStateOf(Size.NADA) }

    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

        TextButton(onClick = { expanded = true }) {
            Text(texto)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Grande") },
                onClick = {
                    expanded = false
                    texto = "Grande"
                    tipo = Size.GRANDE
                },
            )

            DropdownMenuItem(
                text = { Text("Mediano") },
                onClick = {
                    expanded = false
                    texto = "Mediano"
                    tipo = Size.MEDIANO

                },
            )

            DropdownMenuItem(
                text = { Text("Enano") },
                onClick = {
                    expanded = false
                    texto = "Enano"
                    tipo = Size.ENANA
                },
            )

            DropdownMenuItem(
                text = { Text("Nada") },
                onClick = {
                    expanded = false
                    texto = "selecciona tamaño"
                    tipo = Size.NADA
                },
            )
        }
    }

    return tipo
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

@ExperimentalMaterial3Api
@Composable
fun TopBar(contadorCarrito: Int, drawerState: DrawerState, scope: CoroutineScope) {

    TopAppBar(
        title = { Text(text = "Pizzería") },

        navigationIcon = {
            TextButton(onClick = {
                if (drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                } else {
                    scope.launch { drawerState.open() }
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }

        },
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

@Composable
fun Drawer(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    items: List<Screen>
) {
    ModalDrawerSheet(modifier = Modifier.width(200.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            items.forEach { screen ->
                DrawerItem(navController, screen)
                scope.launch { drawerState.close() }
                Spacer(modifier = Modifier.height(10.dp))
            }

            CerrarSesion(navController, drawerState, scope)

        }
    }
}

@Composable
fun DrawerItem(navController: NavController, screen: Screen) {
    NavigationDrawerItem(
        label = {
            Text(
                screen.route,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(1.dp),
        selected = false,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}

@Composable
fun CerrarSesion(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {

    var showDialog by remember { mutableStateOf(false) }

    NavigationDrawerItem(
        label = {
            Text(
                "Cerrar sesion",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(1.dp),
        selected = false,
        onClick = {
            showDialog = !showDialog
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Cierre de sesión")
            },
            text = {
                Text("¿Estás seguro de que quieres cerrar sesión?")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(Screen.Login.route)
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    scope.launch { drawerState.close()}
                    showDialog = false
                }) {
                    Text("No")
                }
            }
        )
    }

}


