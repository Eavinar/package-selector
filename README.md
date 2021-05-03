# package-selector
Select required things from the given list of things based on constraints and requirements and pack them into one package.


# Introduction

This library targets to solve the below problem:
Library receives details information about different things. There is a need to identify with things should be packed into one package.
All packages come with unique identifier, weght and price details. Weight and price for things could not exceed some limitations.
Apart from that there are some other constraints such as package has own weight capacity, price could be limited to some amount.
Library identified things which will not exceed package limit and will have maximum price value. Current logic takes into account that 
in case of the having 2 different packages with same value, only less weight package will be selected.

# Documentation

Add this library into your maven repository.
To start using this library, first there is a need to call the method:
```
com.mobiquity.packer.Packer#pack
```
Method accepts only one String parameter. This parameter should be absolute path to the things details.
The only format of the file described below:
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
```
> 81 is maximum weight capacity of the package. Included things weight cannot exceed this limit.

> : is seperator, between maximum weight capacity and things details.

> (1,53.38,€45)
1. - things details should be wrapped to the brackets. 
1. - First parameter is unique identifier for the package
1. - Second parameter is weight of the thing. Default implementatin doesn't acceot weight more than 100. Floating points should always have 2 digits.
1. - Third parameter is currency and price. Price cannot have floating digits, cannot be more than 100.
1. - Currency should be same accross one package.

All parameters are mandatory. In case of failure Exception will be returned with message of failure.

To include several things into the file just need to seperate them via space symbol.
File can contain several lines. All new lines will be considered as the new things for seperate package.


# Result

Result also will have as much line as provided file had.
If package couldn't be created due to restrictions/constraints then corresponding line will have dash symbol (-).
For example:
```
3,5
-
1
```

# Wrong format of the file.

If format is file includes wrong formatting, Exception ```com.mobiquity.exception.APIException``` will be returned.
Exception message will indicate what possible went wrong.
All other lines also will be skipped from evaluation.

# Constraints

Please see below indicate interface class for the constraints.
```
com.mobiquity.constant.Constants
```

# Implementation notes:

During implementation some patterns were used.
1. Composite pattern. It was used to run validators against the provided file content. Each validator checks only the part its responsibe.
2. Factory pattern. It was used to make choice between different Comparators. It allows to sort things in different orders and 
let to prioritize between packages.
3. Builder Pattern. It was used to build Things objects and Wrapper object around these Things. By this way we are sure that objects
cannot be modified.

