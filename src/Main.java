import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

public class Main {
	public static void main(String[] args) {
        CitiesAndCountries_DAO DAO = new DAO();
        List<City> Cities = DAO.createCity("C:\\Users\\iti\\eclipse-workspace\\Countries_and_Cities\\src\\cities.csv");
        
        //sorting cities list using sortbyroll class
        class Sortbyroll implements Comparator<City>
        {
            // Used for sorting in ascending order of
            // roll number
            public int compare(City a, City b)
            {
                return b.getPopulation() - a.getPopulation();
            }
        }
        Collections.sort(Cities, new Sortbyroll());
        
        //printing cities after sorting
         for(City C:Cities)
         {
         System.out.println(C.getCode()+"  "+C.getName()+"  "+C.getContinent()+"  "+C.getSurface_Area()+"  " + C.getPopulation());
            }
         
         //printing countries
         List<Country> Countries = DAO.createCountry("C:\\Users\\iti\\eclipse-workspace\\Countries_and_Cities\\src\\countries.csv");
         for(Country Co:Countries)
         {
         System.out.println(Co.getCode()+"  "+Co.getName());
            }
         
         //Making Map that contain country as key and list of cities of these country as value
         Map<String, List<String>> countriesMap = new HashMap<>();
         for(Country Co:Countries){
             List<String> countryCities = new LinkedList<>();
             for(City C:Cities){
                 if (Co.getCode().equals(C.getCode())){
                     countryCities.add(C.getName());
                 } 
             }
             countriesMap.put(Co.getName(), countryCities);
         }
         
         
         
//       printing countriesMap 
         System.out.println("\n//////////////////////////////////////////////\n");
         countriesMap.forEach((k, v) ->
                System.out.println("key=" + k + ", value=" + v));
         
         
         Map<String, String> countriesFromCode = new HashMap<>();
         for(Country Co:Countries){
             String Country = new String();
             for(City C:Cities){
                 if (Co.getCode().equals(C.getCode())){
                     Country = Co.getName();
                 } 
             }
             countriesFromCode.put(Co.getCode(), Country);
         }
         
         //working in stream Excercise 3   
         //get max poplution city in every country
            Map<String, City> HieghtestCountryCityPop =  
                Cities.stream().collect(
                    Collectors.groupingBy(City::getCode,
                        Collectors.collectingAndThen(
                            Collectors.reducing((City d1, City d2) -> d1.getPopulation() > d2.getPopulation() ? d1 : d2),
                                Optional::get)));
            
            
            System.out.println("\n//////////////////////////////////////////////\n");
            HieghtestCountryCityPop.forEach((k, v) ->
            System.out.println("For Country : " + countriesFromCode.get(k)+ ", max populotion city : " + v.getName()));
            
            //get max poplution city in every Contient
            Map<String, City> HieghtestContinentCityPop =  
                Cities.stream().collect(
                    Collectors.groupingBy(City::getContinent,
                        Collectors.collectingAndThen(
                            Collectors.reducing((City d1, City d2) -> d1.getPopulation() > d2.getPopulation() ? d1 : d2),
                                Optional::get)));
            
            System.out.println("\n//////////////////////////////////////////////\n");
            HieghtestContinentCityPop.forEach((k, v) ->
                System.out.println("For Continent : " + k+ ", max populotion city : " + v.getName()));
            
            //get max poplution Capital 
            City HieghtestCapitalPop =  
                Cities.stream()
                      .filter(City::IsCapital)
                      .max(Comparator.comparingInt(City::getPopulation))
                      .get();
            
            System.out.println("\n//////////////////////////////////////////////\n");
            System.out.println("Max poploution capital is: " + HieghtestCapitalPop.getName());
               
        
	}
}
    
 