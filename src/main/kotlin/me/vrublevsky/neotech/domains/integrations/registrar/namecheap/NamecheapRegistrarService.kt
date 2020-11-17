package me.vrublevsky.neotech.domains.integrations.registrar.namecheap

import me.vrublevsky.neotech.domains.integrations.registrar.DomainRegistrarInformation
import me.vrublevsky.neotech.domains.integrations.registrar.RegistrarService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty(
    value = ["app.integrations.registrar.namecheap.enabled"],
    havingValue = "true"
)
class NamecheapRegistrarService(
    private val client: NamecheapRegistrarServiceClient
) : RegistrarService {

    override fun getInformation(): DomainRegistrarInformation {
        TODO()
    }
}
