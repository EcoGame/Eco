all:
	make clean
	make build
	make run

run:
	@reset
	@echo "Running..."
	java -Djava.library.path=native -jar build/libs/eco.jar

compile:
	make clean
	@echo "Starting build..."	
	gradle build

clean:
	@echo "Cleaning..."
	gradle clean
	@rm -rf saves
	@rm -rf savetxt
	@rm -rf screenshots
	@echo "Done"
