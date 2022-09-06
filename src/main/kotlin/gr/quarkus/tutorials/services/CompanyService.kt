package gr.quarkus.tutorials.services

import gr.quarkus.tutorials.entities.Company
import gr.quarkus.tutorials.repositories.CompanyRepository
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.core.json.JsonObject
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.Response

@ApplicationScoped
class CompanyService(val companyRepository: CompanyRepository) {

    fun getCompanies(page: Int, size: Int): Uni<MutableList<Company>> =
        companyRepository.getPage(page, size)

    fun getCompany(id: Long): Uni<Company>? =
        companyRepository.findById(id)

    fun createCompany(company: Company): Uni<Company> =
        companyRepository.persistAndFlush(company)

    fun deleteCompany(id: Long): Uni<Boolean> =
        companyRepository.deleteById(id)
            .call { _ -> companyRepository.flush() }
}