package com.betterfly.test.ui.model
import java.io.Serializable

///Objeto con la información  de cada personaje

class Item : Serializable {
    lateinit var id :String
    lateinit var name :String
    lateinit var status :String
    lateinit var species :String
    lateinit var type :String
    lateinit var gender :String
    lateinit var image :String

    lateinit var origin:itemOrigin
    lateinit var location: location
    constructor() {}

    constructor(id: String, name:String,
                status:String,
                species:String,
                type:String,
                gender:String,
                image:String,
                origin:itemOrigin,
                location: location
    ) {

        this.id=id
        this.name = name
        this.status = status
        this.species = species
        this.type = type
        this.gender = gender
        this.origin = origin
        this.location = location
        this.image=image
    }
}