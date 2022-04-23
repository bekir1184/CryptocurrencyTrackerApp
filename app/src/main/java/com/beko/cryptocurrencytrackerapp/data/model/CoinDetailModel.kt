package com.beko.cryptocurrencytrackerapp.data.model

import com.google.gson.annotations.SerializedName

data class CoinDetailModel (
    var symbol: String? = "",
    var name: String? = "",
    var hashing_algorithm: String? = "",
    var description: Description? = null,
    var market_data: MarketData? = null,
    var image: Image? = null,
    @SerializedName("price_change_percentage_24h")
    var priceChangePercentage24h: Double? = 0.0

)



data class MarketData(
    val current_price : CurrentPrice? = null)

data class CurrentPrice(
    val usd: Double? = 0.0)

data class Description(
    val en: String? = ""
)
data class Image(var thumb: String? = "",
                 var small: String? = "",
                 var large: String? = "")
