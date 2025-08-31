package com.example.prueba_tecnica.presentation.biometric

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@Composable
fun BiometricAccess(
    title: String = "Protegido",
    subtitle: String = "Autentícate para ver tus favoritos",
    allowDeviceCredential: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    var canAuth by remember { mutableStateOf(false) }
    var authed by remember { mutableStateOf(false) }
    var lastError by remember { mutableStateOf<String?>(null) }

    val authenticators = if (allowDeviceCredential)
        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
    else
        BIOMETRIC_STRONG

    LaunchedEffect(Unit) {
        canAuth = BiometricManager.from(context)
            .canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS

        if (canAuth) {
            startBiometric(
                activity = activity,
                title = title,
                subtitle = subtitle,
                authenticators = authenticators,
                onSuccess = { authed = true },
                onError = { msg -> lastError = msg }
            )
        } else {
            lastError = "El dispositivo no tiene biometría/credencial configurada."
        }
    }

    when {
        authed -> content()
        else -> LockedView(
            message = lastError ?: "Autenticación requerida",
            onRetry = {
                if (canAuth) {
                    startBiometric(
                        activity = activity,
                        title = title,
                        subtitle = subtitle,
                        authenticators = authenticators,
                        onSuccess = { authed = true },
                        onError = { msg -> lastError = msg }
                    )
                } else {
                    Toast.makeText(context, "Configura biometría o PIN en el sistema", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}

@Composable
private fun LockedView(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(message)
            Spacer(Modifier.height(12.dp))
            Button(onClick = onRetry) { Text("Desbloquear") }
        }
    }
}

private fun startBiometric(
    activity: FragmentActivity,
    title: String,
    subtitle: String,
    authenticators: Int,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)
    val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            onSuccess()
        }
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            onError(errString.toString())
        }
        override fun onAuthenticationFailed() {
            onError("Autenticación fallida, intenta de nuevo.")
        }
    }
    val prompt = BiometricPrompt(activity, executor, callback)
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subtitle)
        .setAllowedAuthenticators(authenticators)
        .build()
    prompt.authenticate(promptInfo)
}
