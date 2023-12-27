package com.example.pruebaunidad_1.Modelo

class CuentaMesa(private val mesa: Int) {
    private val items = mutableListOf<ItemMesa>()
    var aceptarPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val item = ItemMesa(itemMenu, cantidad)
        items.add(item)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        var total = 0
        for (item in items) {
            total += item.calcularSubtotal()
        }
        return total
    }

    fun calcularPropina(): Int {
        val totalSinPropina = calcularTotalSinPropina()
        return (totalSinPropina * 0.1).toInt()
    }

    fun calcularTotalConPropina(): Int {
        val totalSinPropina = calcularTotalSinPropina()
        val propina = calcularPropina()
        return totalSinPropina + if (aceptarPropina) propina else 0
    }
}