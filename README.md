# TagScout

### Summary

This sample add a domain layer between the presenter layer and repositories. This split the app in three layers:

* **MVP**: Model View Presenter pattern 
* **Domain**: Holding all business logic. The domain layer starts with some classes named use cases or interactors and used by the application presenters. These use cases represent all the possible actions a developer can perform with the domain layer. The execution of this Use Cases are in a background thread using the [command pattern](http://www.oodesign.com/command-pattern.html). All the domain layer is completely decoupled from the Android SDK or another third party libraries.
* **Repository**: Repository pattern   

### Key concepts
