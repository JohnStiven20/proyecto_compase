package com.example.loginregistro.data

import androidx.compose.runtime.mutableStateListOf
import com.example.loginregistro.data.modelo.ProductoDTO
import com.example.loginregistro.data.modelo.ProductoEntity
import com.example.loginregistro.data.modelo.Tipo
import modelo.Size

object  Mapper {

       fun  jsonToProducto(listaProductos:List<ProductoEntity>):MutableList<ProductoDTO>{

            var  listaProductosDTO:MutableList<ProductoDTO> = mutableStateListOf();
           listaProductos.forEach {
               listaProductosDTO.add( ProductoDTO(
                   id = it.id ,
                   nombre = it.nombre ,
                   size = Size.valueOf(  if( it.size != null ) it.size.uppercase() else "NADA"  ) ,
                   tipo =  Tipo.valueOf(it.tipo.uppercase() ?: "PASTA"),
                   ingredientes =  it.ingredientes ,
                   precio =  it.precio
               ))

           }

           return  listaProductosDTO;
       }

}