package com.studies.basicassigment.Models

import java.io.Serializable
@kotlinx.serialization.Serializable
data class videomodel(val thumbnail:String,val video:String,val videoduration:String,val title:String,val channelogo:String,val channelname:String): Serializable
