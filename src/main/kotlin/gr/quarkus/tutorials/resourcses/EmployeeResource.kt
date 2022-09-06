package gr.quarkus.tutorials.resourcses

import gr.quarkus.tutorials.entities.Email
import gr.quarkus.tutorials.entities.Employee
import gr.quarkus.tutorials.entities.Phone
import gr.quarkus.tutorials.services.EmployeeService
import gr.quarkus.tutorials.services.InformationService
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import javax.ws.rs.*

@Path("/employees")
@GraphQLApi
class EmployeeResource(val employeeService: EmployeeService, val information: InformationService) {

    @Query("Employees")
    @Description("Get all employees from the data base")
    fun getAll(page: Int = 1, size: Int = 3): Uni<MutableList<Employee>> =
        employeeService.getEmployees(page, size)

    @Query("Employee")
    @Description("Get one employee from the data base")
    fun getOne(id: Long): Uni<Employee>? =
        employeeService.getEmployee(id)

    @Mutation
    @Description("Creates an employee in the database")
    fun createEmployee(employee: Employee) =
        employeeService.createEmployee(employee)

    @Mutation
    @Description("Deletes an employee from the data base")
    fun deleteEmployee(id: Long) = employeeService.delete(id)

    //Phone call methods for employees
    @Mutation
    @Description("Adds a phone to an employee")
    fun addEmployeePhone(phone: Phone, id: Long): Uni<Phone>? =
        information.addPhone(phone, id, "employee")

    @Mutation
    @Description("Deletes phone from employee")
    fun deleteEmployeePhone(employee_id: Long, id: Long): Uni<Long>? =
        information.deletePhone(employee_id, id, "employee")

    //Email call methods for employees
    @Mutation
    @Description("Adds email to employee")
    fun addEmployeeEmail(id: Long, email: Email): Uni<Email>? =
        information.addEmail(id, email, "employee")

    @Mutation
    @Description("Deletes email from employee")
    fun deleteEmployeeEmail(employee_id: Long, id: Long): Uni<Long>? =
        information.deleteEmail(employee_id, id, "employee")

}