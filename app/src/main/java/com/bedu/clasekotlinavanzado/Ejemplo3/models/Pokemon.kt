package com.bedu.clasekotlinavanzado.Ejemplo3.models

//de aquí, recuperaremos tres valores, uno de ellos es un objeto (sprites), por lo tanto, se tiene qué definir otra clase para él
data class Pokemon (
    val name : String? = "",
    val weight: Int? = 0,
    val sprites : Sprites? = null

)