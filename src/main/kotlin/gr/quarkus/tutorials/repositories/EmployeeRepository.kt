package gr.quarkus.tutorials.repositories

import gr.quarkus.tutorials.entities.Employee
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.panache.common.Page
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EmployeeRepository : PanacheRepository<Employee> {

    fun getPage(page: Int, size: Int): Uni<MutableList<Employee>> =
        findAll().page<Employee>(Page.of(page, size)).list()

}