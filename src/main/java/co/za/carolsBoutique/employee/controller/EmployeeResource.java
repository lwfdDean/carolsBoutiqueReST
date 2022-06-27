package co.za.carolsBoutique.employee.controller;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.repository.EmployeeRepositoryImp;
import co.za.carolsBoutique.employee.service.EmployeeIdGenerator;
import co.za.carolsBoutique.employee.service.EmployeeServiceImp;
import co.za.carolsBoutique.employee.service.IServiceEmployee;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employee")
public class EmployeeResource {

    private IServiceEmployee service;

    public EmployeeResource() {
        service = new EmployeeServiceImp(new EmployeeRepositoryImp(), new EmployeeIdGenerator());
    }

    @Path("/register")////////////
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(Employee employee) {
        return Response.status(Response.Status.OK).entity(service.register(employee)).build();
    }

    @Path("/login")/////////
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> loginDetails) {
        return Response.status(Response.Status.OK).entity(service.login(loginDetails)).build();
    }

    @Path("/getAllEmployees/{boutiqueId}")///////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEmployees(@PathParam("boutiqueId") String boutiqueId) {
        return Response.status(Response.Status.OK).entity(service.getAllEmployees(boutiqueId)).build();
    }

    @Path("/getAllByRole/{roleId}/{boutiqueId}")////////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByRole(@PathParam("roleId") String roleId, @PathParam("boutiqueId") String boutiqueId) {
        return Response.status(Response.Status.OK).entity(service.getAllByRole(roleId, boutiqueId)).build();
    }

    @Path("/promoteToTeller")//////////
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response promoteToTeller(List<String> employeeDetails) {
        return Response.status(Response.Status.OK).entity(service.promoteToTeller(employeeDetails)).build();
    }

    @Path("/getRole/{roleId}")////////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRole(@PathParam("roleId") String roleId) {
        return Response.status(Response.Status.OK).entity(service.getRole(roleId)).build();
    }

    @Path("/getAllRoles")///////////////
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        return Response.status(Response.Status.OK).entity(service.getAllRoles()).build();
    }

    @Path("/verifyManagerCode")/////////////
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verifyManagerCode(Map<String, String> managerCode) {
        return Response.status(Response.Status.OK).entity(service.verifyManagerCode(managerCode)).build();
    }

}
