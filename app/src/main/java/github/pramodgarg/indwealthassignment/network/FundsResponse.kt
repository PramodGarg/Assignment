package github.pramodgarg.indwealthassignment.network

data class FundsResponse(val count: Int, val data: List<Fund>)


data class Fund(
        val name: String, val rating: Double?,
        val aum: String,
        val expenseRatio: Double,
        val fundType: String,
        val risk: String,
        val planType: String,
        val returns: Returns?
)

data class Returns(val oneYear: String)