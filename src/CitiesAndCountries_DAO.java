import java.util.List;


public interface CitiesAndCountries_DAO {
    List<String> readFromCSV(String row_file);
    List<City> createCity(String row_file);
    List<Country> createCountry(String row_file);
}