package io.github.emikaelsilveira.resource.viacep.entities

data class ViaCepAddress(
    val cep: String,
    val street: String,
    val complement: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val ibge: String
) {
    // FIXME add funcition on data class AddressDTO
}
