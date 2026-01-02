build:
	javac -d out src/Main.java src/App/*.java src/Entity/Entity.java src/Entity/Piece/*.java src/Entity/Tile/*.java src/Interfaces/*.java src/Images/*.java
	cp -r src/Images out/Images


jar: build
	cd out && jar cf szachy.jar .

run: jar
	#java -cp out/szachy.jar Main
	java -cp out Main
