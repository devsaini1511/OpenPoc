package thedebug.dev.openpoc.data.model

data class Bet(var type: String, var sellIn: Int, var odds: Int, var image: String) {
    override fun toString(): String {
        return this.type + ", " + this.sellIn + ", " + this.odds
    }
}