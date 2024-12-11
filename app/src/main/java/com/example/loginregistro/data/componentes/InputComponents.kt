package com.example.loginregistro.data.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginregistro.R

@Composable
fun CampoTextoPersonalizado(
    text: String,
    Onchage: (String) -> Unit,
    nombreCampo: String,
    tipo: KeyboardType = KeyboardType.Text
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(percent = 50)),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            ),
        value = text,
        onValueChange = Onchage,
        keyboardOptions = KeyboardOptions(keyboardType = tipo),
        placeholder = {Text(nombreCampo)},
        shape = RoundedCornerShape(percent = 50),
        )

    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun CampoTextoPassword(
    text: String,
    Onchage: (String) -> Unit
) {

    var passwordVisible by remember {mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(percent = 50)),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,

            ),
        value = text,
        onValueChange = Onchage,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = { Text("Contraseña") },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(
                    painter = painterResource(id = if (passwordVisible) R.drawable.ojo else R.drawable.esconder),
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        shape = RoundedCornerShape(percent = 50)
    )

    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun MensajeDeError(
    mostrar: Boolean,
    mensaje: String,
    color: Color = MaterialTheme.colorScheme.errorContainer,
    tamañoFuente: TextUnit = 15.sp,
    espacioInferior: Dp = 10.dp
) {
    if (mostrar) {
        Text(
            text = mensaje,
            fontSize = tamañoFuente,
            color = color
        )
        Spacer(modifier = Modifier.height(espacioInferior))
    }
}



