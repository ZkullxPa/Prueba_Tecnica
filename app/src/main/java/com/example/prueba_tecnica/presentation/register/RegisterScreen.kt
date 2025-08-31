package com.example.prueba_tecnica.presentation.register

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.prueba_tecnica.R
import com.example.prueba_tecnica.presentation.login.UserState
import com.example.prueba_tecnica.presentation.login.UserViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: UserViewModel){
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(activity = context as ComponentActivity)
    val digitalColor: Color = colorResource(R.color.bluelight)
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state) {
        if (state is UserState.Success) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    val boxModifier = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> Modifier
            .width(450.dp)
            .padding(8.dp)
        WindowWidthSizeClass.Medium -> Modifier
            .width(800.dp)
            .padding(10.dp)
        WindowWidthSizeClass.Expanded -> Modifier
            .width(200.dp)
            .background(Color.Blue)
            .padding(24.dp)
        else -> Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(8.dp)
    }
    val loginModifier = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
        WindowWidthSizeClass.Medium -> Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
        WindowWidthSizeClass.Expanded -> Modifier
            .width(200.dp)
            .padding(24.dp)
        else -> Modifier
            .fillMaxWidth()
            .padding(8.dp)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(digitalColor)
            .imePadding(),
        contentAlignment = Alignment.Center
    ){
        RegisterBody(boxModifier, loginModifier, navController,  onRegisterClick = { email, password, name ->
            viewModel.register(email, password, name)
        }, uiState = state)
    }
}
@Composable
fun RegisterBody(boxModifier: Modifier, loginModifier: Modifier, navController: NavController,  onRegisterClick: (String, String, String) -> Unit, uiState: UserState){
    val logoState = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val backgrounColor = MaterialTheme.colorScheme.background
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var errosSession by remember { mutableStateOf(false) }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = canvasWidth + 500, y = canvasHeight - 1200),
            color = Color.White,
            strokeWidth = 1400f
        )
    }
    Column(
        modifier = boxModifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterHeader(logoState = logoState.value)
        Card(
            modifier = Modifier
                .width(420.dp)
                .padding(20.dp)
                .background(Color.Transparent),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 50.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
                NameField(
                    logoState = logoState,
                    loginModifier,
                    email = name,
                    onEmailChange = { name = it})
                Spacer(modifier = Modifier.padding(10.dp))
                EmailField(
                    logoState = logoState,
                    loginModifier,
                    email = email,
                    onEmailChange = { email = it})
                Spacer(modifier = Modifier.padding(12.dp))
                PasswordField(
                    logoState = logoState,
                    loginModifier,
                    password= password,
                    onPasswordChange = { password = it })
                Spacer(modifier = Modifier.padding(12.dp))
                Button( //Boton para el inicio de sesion
                    onClick = {
                        onRegisterClick(email.trim(), password, name.trim())
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.bluelight))
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(12.dp))
                if (errosSession){
                    Text(
                        text = "No se pudo crear la cuenta",
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.red_disconnected)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RegisterHeader(logoState: Boolean){
    val context = LocalContext.current
    val windowSizeClass = calculateWindowSizeClass(activity = context as ComponentActivity)
    val logoModifier = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> Modifier
            .width(105.dp)
            .padding(8.dp)
        WindowWidthSizeClass.Medium -> Modifier
            .width(110.dp)
            .padding(bottom = 10.dp)
        WindowWidthSizeClass.Expanded -> Modifier
            .width(200.dp)
            .padding(24.dp)
        else -> Modifier
            .fillMaxWidth()
            .padding(8.dp)
    }
    if (logoState){
        Image(
            painterResource(R.drawable.logo_letra), "Content description",
            modifier = logoModifier,
            contentScale = ContentScale.Fit
        )
    }
}
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun EmailField(logoState: MutableState<Boolean>, loginfield: Modifier, email: String, onEmailChange: (String) -> Unit){
    Log.d("LogoState: ", "$logoState")
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = loginfield
            .height(65.dp)
            .onFocusChanged { focusState ->
                logoState.value = !focusState.isFocused
                Log.d("LogoState: ", "$logoState")
            },
        label = { Text("Correo Electronico") },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = colorResource(R.color.light_gray),
            unfocusedContainerColor = colorResource(R.color.light_gray),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                logoState.value = true
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                Log.d("LogoState: ", "$logoState")
            }
        )
    )
}
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NameField(logoState: MutableState<Boolean>, loginfield: Modifier, email: String, onEmailChange: (String) -> Unit){
    Log.d("LogoState: ", "$logoState")
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = loginfield
            .height(65.dp)
            .onFocusChanged { focusState ->
                logoState.value = !focusState.isFocused
                Log.d("LogoState: ", "$logoState")
            },
        label = { Text("Nombre") },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = colorResource(R.color.light_gray),
            unfocusedContainerColor = colorResource(R.color.light_gray),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                logoState.value = true
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                Log.d("LogoState: ", "$logoState")
            }
        )
    )
}
//Composable para el formulario del password
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PasswordField(logoState: MutableState<Boolean>, passwordfield: Modifier, password: String, onPasswordChange: (String) -> Unit){
    Log.d("LogoState: ", "$logoState")
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = passwordfield
            .height(65.dp)
            .onFocusChanged { focusState ->
                logoState.value = !focusState.isFocused
                Log.d("LogoState: ", "$logoState")
            },
        trailingIcon = trailingIcon,
        label = { Text("Contraseña") },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            focusedContainerColor = colorResource(R.color.light_gray),
            unfocusedContainerColor = colorResource(R.color.light_gray),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrect = false
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                logoState.value = true
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
                Log.d("LogoState: ", "$logoState")
            }
        ),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
