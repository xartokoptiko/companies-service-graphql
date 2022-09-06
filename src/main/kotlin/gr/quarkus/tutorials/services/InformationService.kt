package gr.quarkus.tutorials.services

import gr.quarkus.tutorials.entities.*
import gr.quarkus.tutorials.repositories.EmailRepository
import gr.quarkus.tutorials.repositories.LocationRepository
import gr.quarkus.tutorials.repositories.PhoneRepository
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class InformationService(
    val phoneRepository: PhoneRepository,
    val emailRepository: EmailRepository,
    val locationRepository: LocationRepository
) {

    //Phone services
    fun addPhone(phone: Phone, id: Long, toEntity: String): Uni<Phone>? {

        val company = Company()
        company.id = id
        val employee = Employee()
        employee.id = id

        when (toEntity) {
            "employee" -> phone.employee = employee
            "company" -> phone.company = company
            else -> Log.info("Entity not found")
        }
        return phoneRepository.persistAndFlush(phone)
    }

    fun deletePhone(entity_id: Long, id: Long, toEntity: String): Uni<Long>? {
        var query: String = ""
        when (toEntity) {
            "company" -> query = "company_id=?1 AND id=?2"
            "employee" -> query = "employee_id=?1 AND id=?2"
        }

        return phoneRepository.delete(query, entity_id, id)
            .call { _ -> phoneRepository.flush() }
    }

    //Email services
    fun addEmail(id: Long, email: Email, toEntity: String): Uni<Email>? {

        val company = Company()
        company.id = id
        val employee = Employee()
        employee.id = id

        when (toEntity) {
            "employee" -> email.employee = employee
            "company" -> email.company = company
            else -> Log.info("Entity not found")
        }

        return emailRepository.persistAndFlush(email)
    }

    fun deleteEmail(entity_id: Long, id: Long, toEntity: String): Uni<Long>? {

        var query: String = ""
        when (toEntity) {
            "company" -> query = "company_id=?1 AND id=?2"
            "employee" -> query = "employee_id=?1 AND id=?2"
        }

        return emailRepository.delete(query, entity_id, id)
            .call { _ -> emailRepository.flush() }
    }

    //Location services
    fun addLocation(location: Location, id: Long): Uni<Location>? {

        val company = Company()
        company.id = id
        location.company = company

        return locationRepository.persistAndFlush(location)
    }

    fun deleteLocation(company_id: Long, id: Long): Uni<Long>? =
        locationRepository.delete("company_id=?1 AND id=?2", company_id, id)
            .call { _ -> locationRepository.flush() }
}