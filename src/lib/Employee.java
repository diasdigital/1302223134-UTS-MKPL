package lib;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String employeeId;
	private String idNumber;
	private String address;
	private boolean isForeigner;
	private boolean gender; // true = Laki-laki, false = Perempuan

	private FullName fullName;
	private Spouse spouse;
	private JoiningDate joiningDate;
	private List<Child> children;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	private int monthWorkingInYear;

	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean gender) {
		this.employeeId = employeeId;
		this.idNumber = idNumber;
		this.address = address;
		this.isForeigner = isForeigner;
		this.gender = gender;

		this.fullName = new FullName(firstName, lastName);
		this.joiningDate = new JoiningDate(yearJoined, monthJoined, dayJoined);
		this.children = new LinkedList<>();
	}

	public void setMonthlySalary(int grade) {
		if (grade == 1) {
			monthlySalary = 3000000;
		} else if (grade == 2) {
			monthlySalary = 5000000;
		} else if (grade == 3) {
			monthlySalary = 7000000;
		}

		if (isForeigner) {
			monthlySalary *= 1.5;
		}
	}

	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	public void setSpouse(String name, String idNumber) {
		this.spouse = new Spouse(name, idNumber);
	}

	public void addChild(String name, String idNumber) {
		children.add(new Child(name, idNumber));
	}

	public int getAnnualIncomeTax() {
		LocalDate date = LocalDate.now();
		if (date.getYear() == joiningDate.year) {
			monthWorkingInYear = date.getMonthValue() - joiningDate.month;
		} else {
			monthWorkingInYear = 12;
		}

		boolean isSingle = (spouse == null);
		return TaxFunction.calculateTax(
			monthlySalary,
			otherMonthlyIncome,
			monthWorkingInYear,
			annualDeductible,
			isSingle,
			children.size()
		);
	}

	private static class FullName {
		private String firstName;
		private String lastName;

		public FullName(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}
	}

	private static class Spouse {
		private String name;
		private String idNumber;

		public Spouse(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}

	private static class JoiningDate {
		private int year;
		private int month;
		private int day;

		public JoiningDate(int year, int month, int day) {
			this.year = year;
			this.month = month;
			this.day = day;
		}
	}

	private static class Child {
		private String name;
		private String idNumber;

		public Child(String name, String idNumber) {
			this.name = name;
			this.idNumber = idNumber;
		}
	}
}
