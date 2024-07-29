package org.rajeshdyadav.grpc.client;

import org.rajeshdyadav.grpc.stubs.employee.EmployeeAddRequest;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeAddResponse;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeData;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeSearchRequest;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeSearchResponse;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeServiceGrpc;

public class EmployeeClient {

    private final EmployeeServiceGrpc.EmployeeServiceBlockingStub blockingStub;

    public EmployeeClient() {
        blockingStub = EmployeeServiceGrpc.newBlockingStub(MyGrpcClient.getChannel());
    }

    public static void main(String[] args) {
        EmployeeClient employeeClient = new EmployeeClient();
        employeeClient.addEmployee();
        employeeClient.listEmployees();
    }

    void listEmployees() {
        EmployeeSearchRequest request = EmployeeSearchRequest.newBuilder()
                //.setEmpId(1001)
                .build();
        EmployeeSearchResponse response = blockingStub.employeeSearch(request);
        response.getEmpDataList().forEach(employeeData -> {
            System.out.printf("%d %s %s %s%n", employeeData.getEmpId(), employeeData.getEmpName(), employeeData.getEmail(), employeeData.getPhoneNumber());
        });
    }

    void addEmployee() {
        EmployeeData employeeData = EmployeeData.newBuilder()
                //.setEmpId(1002)
                .setEmpName("Raj").setEmail("raj7749@gmail.com").setPhoneNumber("100").build();

        EmployeeAddRequest addRequest = EmployeeAddRequest.newBuilder().setEmpData(employeeData).build();

        EmployeeAddResponse response = blockingStub.employeeAdd(addRequest);
        System.out.printf("%d %s %s %n", response.getEmpData().getEmpId(), response.getStatus().getMsgId(), response.getStatus().getMsgDesc());
    }
}
