# Spring-Boot_Cache_using_Hazelcast
Spring-Boot Cache using Hazelcast  ||  Cache Mechanism never works on ResponseEntity&lt;._.> type

# Steps To Enable cache:
	  1. Add Dependencies
	  2. Create cache Configuration
	  3. Enable and Use caching
	  		A.Enable caching
	  			i. Add @EnableCaching in main.
	  	    ii.Implements Serializable on Entity.java & hit "Ctrl+1" to default serialVersionUID.
	     B.Use caching on Controller
	       	i. Add @Cacheable("user-cache") with Cache name.
	         ii.Add @Transactional(true) for read(R) operation else (false) for CU.
	  4. Evict cache
	  		i. Add @CacheEvict("user-cache") with Cache name on Delete(D) method.
	  
	  Note: Cache Mechanism never works on ResponseEntity<._.> type
