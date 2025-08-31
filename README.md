# Prueba\_Tecnica



Requisitos

* Android Studio Narwhal 
* JDK 17
* Gradle wrapper del repo
* Dispositivo o emulador con Android 8.0+



Abrir en Android Studio

1. Open -> selecciona la carpeta del proyecto
2. Espera el sync de Gradle
3. Aceptar las actualizaciones de los plugins



Configuración Rapida

El proyecto usa:

* Kotlin coroutines
* Hilt con KSP
* Jetpack Compose
* Room
* Retrofit + Gson
* Biometric
* MockK para tests

Si falla la sincronizacion revisa que tengas estas dependencies en app/build.gradle:

dependencies{

&nbsp;	androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

&nbsp;   implementation ("androidx.compose.material:material-icons-extended:1.4.3")

&nbsp;   implementation(libs.material3)

&nbsp;   implementation(libs.androidbrowserhelper)

&nbsp;   ksp("com.google.dagger:hilt-android-compiler:2.51.1")

&nbsp;   implementation("io.coil-kt:coil-compose:2.6.0")

&nbsp;   implementation("io.socket:socket.io-client:2.0.0")

&nbsp;   implementation ("androidx.room:room-runtime:2.5.0")

&nbsp;   ksp ("androidx.room:room-compiler:2.5.0")

&nbsp;   implementation("androidx.biometric:biometric:1.1.0")

&nbsp;   implementation ("androidx.room:room-ktx:2.5.0")

&nbsp;   implementation("com.google.dagger:hilt-android:2.51.1")

&nbsp;   implementation("com.google.code.gson:gson:2.10.1")

&nbsp;   implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

&nbsp;   implementation ("androidx.lifecycle:lifecycle-livedata-ktx:1.2.1")

&nbsp;   implementation("androidx.compose.runtime:runtime-livedata:1.6.0")

&nbsp;   implementation ("com.squareup.retrofit2:retrofit:2.11.0")

&nbsp;   implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

&nbsp;   implementation ("androidx.navigation:navigation-compose:2.9.0")

&nbsp;   implementation ("androidx.compose.ui:ui:1.5.4")

&nbsp;   implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

&nbsp;   implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

&nbsp;   implementation("androidx.compose.material3:material3:1.4.0-alpha15")

&nbsp;   implementation ("androidx.compose.material3:material3-window-size-class:1.1.0")

&nbsp;   implementation ("androidx.compose.foundation:foundation:1.6.8")

&nbsp;   implementation ("com.google.accompanist:accompanist-pager:0.34.0")

&nbsp;   implementation("com.google.android.gms:play-services-maps:19.0.0")

&nbsp;   implementation("com.google.maps.android:maps-compose:4.3.3")

&nbsp;   testImplementation("junit:junit:4.+")

&nbsp;   testImplementation("io.mockk:mockk:1.12.2")

&nbsp;   testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

}



Ejecutar la app

1. Para ejecutar la aplicación conecta un dispositivo en modo desarrollador o crea un emulador
2. En Android Studio elige la opción de run app
3. Ejecuta la aplicación



Acceso

1. Debes de crear una cuenta local, al no contar con servidor se hizo una base local con room para el registro

2\. Debes ingresar correo y contraseña para ingresar a la aplicación

3\. Una vez dentro la primera pantalla sera la de personajes



Personajes

* Paginacion e infinito scroll
* La lista de personajes carga mas paginas al llegar cerca del final
* Si no ves mas items revisa tu red
* Filtros y busqueda
* La barra de busqueda filtra por nombre



Favoritos y persistencia

* Favoritos se guardan en Room
* Episodios vistos se guardan en SharedPreferences por usuario y personaje



Biometrico para Favoritos

La pantalla de favoritos esta protegida con BiometricPrompt

En caso de no tener huella o pin configurado no tendrás acceso a esta pantallas


Imagenes de muestra

Login
<img width="610" height="1356" alt="image" src="https://github.com/user-attachments/assets/c582b649-d732-40d2-a7b7-18a824505103" />





