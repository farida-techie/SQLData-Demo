package com.malkinfo.addinformationsqldatabes.databes

class Contacts {
    var id :Int = 0
    var name :String
    var phno :String
    internal constructor(name :String,phno:String){
        this.name = name
        this.phno = phno
    }
    internal constructor(id:Int,name :String,phno:String){
        this.id = id
        this.name = name
        this.phno = phno
    }

}