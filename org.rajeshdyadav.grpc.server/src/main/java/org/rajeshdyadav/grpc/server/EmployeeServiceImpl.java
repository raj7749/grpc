package org.rajeshdyadav.grpc.server;

import org.rajeshdyadav.grpc.stubs.employee.EmployeeAddRequest;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeAddResponse;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeData;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeSearchRequest;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeSearchResponse;
import org.rajeshdyadav.grpc.stubs.employee.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.rajeshdyadav.grpc.stubs.employee.Status;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    private final List<EmployeeData> employees = new CopyOnWriteArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @Override
    public void employeeAdd(EmployeeAddRequest request, StreamObserver<EmployeeAddResponse> responseObserver) {
        // Generate a new unique emp_id
        int newEmpId = idCounter.incrementAndGet();

        // Create a new employee with the generated emp_id
        EmployeeData newEmployee = EmployeeData.newBuilder()
                .setEmpId(newEmpId)
                .setEmpName(request.getEmpData().getEmpName())
                .setEmail(request.getEmpData().getEmail())
                .setPhoneNumber(request.getEmpData().getPhoneNumber())
                .build();

        employees.add(newEmployee);

        Status status = Status.newBuilder()
                .setMsgId("200")
                .setMsgDesc("Employee added successfully")
                .build();

        EmployeeAddResponse response = EmployeeAddResponse.newBuilder()
                .setEmpData(newEmployee)
                .setStatus(status)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void employeeSearch(EmployeeSearchRequest request, StreamObserver<EmployeeSearchResponse> responseObserver) {
        List<EmployeeData> matchedEmployees;

        // If neither emp_id nor emp_name is set, return all employees
        if (!request.hasEmpId() && !request.hasEmpName()) {
            matchedEmployees = employees;
        } else {
            matchedEmployees = employees.stream()
                    .filter(emp -> (request.hasEmpId() && emp.getEmpId() == request.getEmpId()) ||
                            (request.hasEmpName() && emp.getEmpName().equalsIgnoreCase(request.getEmpName())))
                    .collect(Collectors.toList());
        }

        EmployeeSearchResponse response = EmployeeSearchResponse.newBuilder()
                .addAllEmpData(matchedEmployees)
                .setRequestData(request)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
