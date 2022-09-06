package gr.quarkus.tutorials.resourcses

import gr.quarkus.tutorials.entities.Company
import gr.quarkus.tutorials.entities.Email
import gr.quarkus.tutorials.entities.Location
import gr.quarkus.tutorials.entities.Phone
import gr.quarkus.tutorials.services.CompanyService
import gr.quarkus.tutorials.services.InformationService
import io.smallrye.mutiny.Uni
import io.vertx.core.json.JsonObject
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import javax.annotation.security.RolesAllowed
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@GraphQLApi
@Path("/companies")
class CompanyResource(val companyService: CompanyService, val information : InformationService) {

    @Query("Companies")
    @Description("Get all companies from data base ")
    fun getEmployees(page: Int = 1, size: Int = 3) : Uni<MutableList<Company>> =
        companyService.getCompanies(page, size)

    @Query("Company")
    @Description("Get one company from the database")
    fun getOne(id : Long) : Uni<Company>? =
        companyService.getCompany(id)

    @Mutation
    fun createCompany(company: Company) =
        companyService.createCompany(company)

    @Mutation
    fun deleteCompany(id: Long)= companyService.deleteCompany(id)


    @Mutation
    fun addCompanyPhone(id: Long, phone: Phone): Uni<Phone>? =
        information.addPhone(phone, id, "company")

    @Mutation
    fun deleteCompanyPhone(company_id: Long, id: Long): Uni<Long>? =
        information.deletePhone(company_id, id, "company")

    //Location call-Methods for Companies

    @Mutation
    fun addCompanyLocation(id: Long, location: Location): Uni<Location>? =
        information.addLocation(location, id)

    @Mutation
    fun deleteCompanyLocation(company_id: Long, id: Long): Uni<Long>? =
        information.deleteLocation(company_id, id)

    //Email call-methods for companies

    @Mutation
    fun addCompanyEmail(id: Long, email: Email): Uni<Email>? = information.addEmail(id, email, "company")

    @Mutation
    fun deleteCompanyEmail(company_id: Long, id: Long): Uni<Long>? =
        information.deleteEmail(company_id, id, "company")

}