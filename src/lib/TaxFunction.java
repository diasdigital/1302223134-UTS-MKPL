package lib;

public class TaxFunction {

	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */

	private static final int BASIC_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_TAX_RELIEF = 4500000;
	private static final int CHILD_TAX_RELIEF = 1500000;
	private static final double TAX_PERCENTAGE = 0.05;
	private static final int MAX_CHILDREN_TAX_RELIEF = 3;
	
	
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
		
		int tax = 0;
		
		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}
		
		if (numberOfChildren > MAX_CHILDREN_TAX_RELIEF) {
			numberOfChildren = MAX_CHILDREN_TAX_RELIEF;
		}
		
		if (isMarried) {
			tax = (int) Math.round(TAX_PERCENTAGE * (((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking)
				- deductible - (BASIC_NON_TAXABLE_INCOME + MARRIED_TAX_RELIEF + (numberOfChildren * CHILD_TAX_RELIEF))));
		}else {
			tax = (int) Math.round(TAX_PERCENTAGE * (((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - BASIC_NON_TAXABLE_INCOME));
		}
		
		if (tax < 0) {
			return 0;
		}else {
			return tax;
		}
			 
	}
	
}
