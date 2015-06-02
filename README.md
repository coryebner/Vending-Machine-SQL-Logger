Note: Note: The website where this logger connected to was removed. You can host your own from the code located at: https://bitbucket.org/jimmy-the-eek/database

The code will then need to be modified to point at the new host

## Quick Example

```java
// Grab your API token from your profile page
Rifffish r = new Rifffish("rsh_3wL4MyhWW4z3kfjoYfyN0gtt");


// We want to add a new machine and add two products to it.
Machine newMachine = r.createMachine(new Machine("Super Fun Time", "vmrs_sff_p_c", "in_service", "CAD"));

// Now for the two products
Product productOne = r.createProduct(new Product(newMachine.getId(), "Dr. Skipper", 100, 10, 30));
Product productTwo = r.createProduct(new Product(newMachine.getId(), "Pepsi Awesome", 665, 6, 30));

// Ooops, Dr. Pepper is sending the lawyers. Time for a product Name change!
productOne.setName("Totally Not Dr. Pepper");
r.updateProduct(productOne);


// Transaction Occurred, now we want to log it.

// Create a new Transaction 
// (for machine 1, product 1, with coins, and payment successful) 
// & Log It

// new Transaction(ProductId, PaymentMethod, PaymentStatus)

Error e = r.log(new Transaction(productOne.getId(), PaymentMethod.COIN, true));

// If no error is found, then e will be null. Else you can print out the error.
System.out.println(e);
```


## Documentation

### Machine Management
This allows remote management of a machine. All fields are required.

#### The Machine Object:
```java
  private int id = -1;
  private String name = null;
  private String config_name = null;
  private String mode = null;
  private String currency = null;
```

Configuration name is not currently not an ENUM. This may change.
```java
vmrs_sff_p_c: "VMRUS-SFF-P/C"
vmrs_sff_p_ci: "VMRUS-SFF-P/CI"
vmrs_sff_p_pi: "VMRUS-SFF-P/PI"
vmrs_sff_p_mi: "VMRUS-COM-P/MI"
vmrs_com_p_m: "VMRUS-COM-P/M"
vmrs_toc_p_mi: "VMRUS-TOC-P/MI"
vmrs_toc_p_i: "VMRUS-TOC-P/I"
vmrus_com_c_mi: "VMRUS-COM-C/MI"
vmrus_com_c_m: "VMRUS-COM-C/M"
vmrus_toc_c_mi: "VMRUS-TOC-C/MI"
vmrus_toc_c_plus: "VMRUS-TOC-C+"
vmrus_toc_c_plus_i: "VMRUS-TOC-C+/I"
```
An example config_name would be: `"vmrs_sff_p_c"`

Configuration mode is currently not an ENUM. This may change.
```java
in_service: "In Service"
needs_service: "Needs Service"
out_of_order: "Out of Order"
```
An example of a mode would be: `"in_service"`


#### Creating a New Machine:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");
Machine m = r.createMachine(new Machine(String name, String configName, String mode, String currency));
```

This will return the Machine object `m`.

Getter's and setters are available. To get the id of this machine you would call:
```java
m.getId();
```
**If the Machine is null, then you weren't able to create the machine successfully.**


#### Updating a Machine:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// machine is already set, but you want to change its name:
machine.setName("Awesome Candy 3000 Super Fun Time");

Machine mUpdated = r.updateMachine(machine);

// This will return the same machine (well it would have the same ID, but it's name is now changed) 
```
**If the Machine is null, then you weren't able to update the machine successfully.**


#### Getting a Machine By ID:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// machine is already set, but you want to change its name:
Machine getMachine = r.getMachine(1);

// This will return the machine that has an ID of 1
```
**If the Machine is null, then a machine with that ID does not exist.**


#### Deleting a Machine By ID:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// machine is already set, but you want to change its name:
Error error = r.deleteMachine(1);

// This will delete the machine that has an ID of 1
```

**If the error is not null, then you weren't able to delete the machine.**


### Product Management
This allows remote management of a product. All fields are required.

#### The Product Object:
```java
  private int id = -1;
  private int machine_id = -1;
  private String name = null;
  private int price = -1;
  private int current_stock_level = -1;
  private int max_stock_level = -1;
```

**Prices are all in cents.**

### Creating a New Product:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");
Product p = r.createProduct(new Product(int machine_id, String name, int price, int current_stock_level, int max_stock_level));
```

This will return the Product object `p`.

Getter's and setters are available. To get the id of this machine you would call:
```java
p.getId();
```
**If the Product is null, then you weren't able to create the product successfully.**


#### Updating a Product:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// product is already set, but you want to change its name:
product.setName("Dr. Skipper");

product pUpdated = r.updateProduct(product);

// This will return the same product (well it would have the same ID, but it's name is now changed) 
```
**If the Product is null, then you weren't able to update the product successfully.**


#### Getting a Product By ID:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// product is already set, but you want to change its name:
Product getProduct = r.getProduct(1);

// This will return the product that has an ID of 1
```
**If the Product is null, then a product with that ID does not exist.**


#### Deleting a Product By ID:
```java
Rifffish r = new Rifffish("rsh_rDWPv1x18utNfeDOqmeQrgtt");

// product is already set, but you want to change its name:
Error error = r.deleteProduct(1);

// This will delete the product that has an ID of 1
```

**If the error is not null, then you weren't able to delete the product.**


### Logging
**Logging current supports multi-threading support. It's probably best to implement the Logger using the singleton pattern to avoid concurrency problems.**

We support three different types of Logging - Transactions, Stockouts, and Problems 

#### To Create an offline logger:
```java
Logger logger = new Logger()
```

#### To Create a logger that sends messages immediately to the API:
```java
// new Logger(RIFFFISH_API_KEY, 0 - zero indicates immediate logging, MachineID)
Logger logger = new Logger("rsh_rDWPv1x18utNfeDOqmeQrgtt", 0, 4)
```

#### To Create a logger that sends messages after a set number of transactions:
```java
// new Logger(RIFFFISH_API_KEY, number of transactions, machineID)
Logger logger = new Logger("rsh_rDWPv1x18utNfeDOqmeQrgtt", number of transactions, machineId)
```

#### To Create a logger that sends messages at a set time:
```java
// new Logger(RIFFFISH_API_KEY, LogDate - for sending transactions at a given date, MachineID)

Logger logger = new Logger("rsh_rDWPv1x18utNfeDOqmeQrgtt", new LogDate(), MachineID)
```

#### LogDate format:
```java
LogDate(logger.LogDate.LoggingType type,
               int startDay,
               int startHour,
               int startMinute)
```

Date object to store info needed for Set time logging

Parameters:
  type - A LoggingType enum
  startDay - Day to send logs to the server. All future logs are based on this date.
  startHour - Hour to send the logs to the server
  startMinute - Minute to send the logs to the server

LoggingType enum:
  DAILY, WEEKLY, MONTHLY

#### How to log a transaction:
```java
// new Transaction (productID from Rifffish - server, quantity - usually 1, Payment Method Enum)
Logger.log(new Transaction (int productID, int quantity, PaymentMethod.COIN))
```

#### Payment Method Enum:
  COIN, CREDIT_CARD, PAYPAL, MIXED

#### Logging Transaction Examples:
```java
Logger.log(new Transaction(1, PaymentMethod.COIN, true));
Logger.log(new Transaction(2, PaymentMethod.CREDIT_CARD, false));
```

#### How to log a Stockout:
```java
Logger.log(new Stockout(java.lang.Integer productId, Rifffish.StockoutTypes type))
```

#### Stockout Types Enum:
  OUTOFSTOCK, ALMOSTOUT

#### Logging Stockout Examples:
```java
Logger.log(new Stockout(1, StockoutType.ALMOSTOUT ));
Logger.log(new Stockout(2, StockoutType.OUTOFSTOCK ));
```

## Changelog
- See our [changelog for SDK changes](https://github.com/tamcgoey/rifffish-developer/tree/master/sdk_java/releases/changelog.md).
