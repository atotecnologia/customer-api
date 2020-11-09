package br.com.composable.customerapi.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import br.com.composable.customerapi.model.Address;

public interface AddressDAO extends Transactional<AddressDAO>{
	
    @SqlUpdate("INSERT INTO address(customer_id, street, state, city, neighborhood, zipCode, number, additionalInformation, major) "
    		+ " VALUES(:customerId, :street, :state, :city, :neighborhood, :zipCode, :number, :additionalInformation, :major)")
    @GetGeneratedKeys
    Long insert(@BindBean Address address);
    
    @RegisterBeanMapper(Address.class)
    @SqlQuery("SELECT * FROM address a "
    		+ "WHERE "
    		+ "a.customer_id=:customerId ")
    List<Address> summary(@Bind("customerId") Long id);
    
    @RegisterBeanMapper(Address.class)
    @SqlQuery("SELECT * FROM address a WHERE a.customer_id=:customerId")
    Address retrieveByCustomer(@Bind("customerId") Long id);
    
    @SqlUpdate("UPDATE address SET "
    		+ "street=:street, state=:state, city=:city, neighborhood=:neighborhood, "
    		+ "zipCode=:zipCode, number=:number, additionalInformation=:additionalInformation, major=:major "
    		+ "WHERE customer_id=:customerId ")
    void updateByCustomer(@BindBean Address address);
    
    @SqlUpdate("DELETE FROM address "
    		+ "WHERE customer_id=:customerId ")
    void deleteByCustomer(@Bind("customerId") Long customerId);
    
    @RegisterBeanMapper(Address.class)
    @SqlQuery("SELECT * FROM address a "
    		+ "WHERE "
    		+ "a.customer_id=:customerId "
    		+ "AND "
    		+ "a.id=:addressId ")
    Address retrieveByCustomerAddress(@Bind("customerId") Long idCustomer,
    								  @Bind("addressId")  Long idAddress);
    
    @SqlUpdate("UPDATE address SET "
    		+ "street=:street, state=:state, city=:city, neighborhood=:neighborhood, "
    		+ "zipCode=:zipCode, number=:number, additionalInformation=:additionalInformation, major=:major "
    		+ "WHERE "
    		+ "customer_id=:idCustomer "
    		+ "AND "
    		+ "id=:idAddress")
    void update(@Bind("idCustomer") Long idCustomer,
			  	@Bind("idAddress")  Long idAddress,
			  	@BindBean Address address);
    
    @SqlUpdate("DELETE FROM address "
    		+ "WHERE "
    		+ "customer_id=:customerId "
    		+ "AND "
    		+ "id=:addressId ")
    void delete(@Bind("customerId") Long idCustomer,
			    @Bind("addressId")  Long idAddress);
}
