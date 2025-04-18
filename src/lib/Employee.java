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
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	 public void setMonthlySalary(int grade) {
		int baseSalary = 0;
		if (grade == 1) {
			baseSalary = 3000000;
		} else if (grade == 2) {
			baseSalary = 5000000;
		} else if (grade == 3) {
			baseSalary = 7000000;
		}
	
		if (isForeigner) {
			baseSalary *= 1.5;
		}
	
		this.monthlySalary = baseSalary;
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
		int monthWorkingInYear = calculateMonthsWorkedThisYear();
		boolean single = isSingle();
		return TaxFunction.calculateTax(
			monthlySalary,
			otherMonthlyIncome,
			monthWorkingInYear,
			annualDeductible,
			single,
			children.size()
		);
	}

	private int calculateMonthsWorkedThisYear() {
		LocalDate now = LocalDate.now();
		if (now.getYear() == joiningDate.year) {
			return now.getMonthValue() - joiningDate.month;
		}
		return 12;
	}
	
	private boolean isSingle() {
		return spouse.idNumber == null || spouse.idNumber.isBlank();
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
