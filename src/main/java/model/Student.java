package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private  String studentId;
    private String firstName;
    private String lastName;
    private String districtId;
    private String year;
    private String month;
    private String day;
    private String schoolId;
    private String schoolName;
    private String schoolCode;
}