package in.ar.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class App {
	private Long caseNum;
	private String fullname;
	private String email;
	private String mobileNumber;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	private Long userId;
}
