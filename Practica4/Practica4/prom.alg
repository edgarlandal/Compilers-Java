inicio-programa
	leer "Cuantas calificaciones",n
	prom = 0
	
	i = 0

	mientras (i < n)
		leer "Da una calificacion",cal
		prom = prom + cal
	fin-mientras
	
	prom = prom/n
	
	si (prom > 5) entonces
		escribir "Aprobado con: ", prom
	fin-si

	si (prom < 6) entonces 
		escribir "Reprobado con: ", prom
	fin-si

	si (prom == 10) entonces
		escribir "Excelente"
	fin-si
fin-programa