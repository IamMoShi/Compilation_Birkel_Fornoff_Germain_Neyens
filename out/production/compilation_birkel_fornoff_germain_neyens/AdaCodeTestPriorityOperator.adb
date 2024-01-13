with Ada.Text_IO; use Ada.Text_IO;

procedure AdaCodeTestPriorityOperator is
    a, b, c, d, e : integer;
    result1, result2 : integer;
begin
    -- Initialisation des variables
    a := 5;
    b := 3;
    c := 2;
    d := 4;
    e := 6;

    -- Expression sans parenthèses où la priorité opératoire est importante
    result1 := a + b * c - d / e;
    
    -- La même expression avec parenthèses pour forcer la priorité
    result2 := (a + b) * (c - d) / e;

    -- Affichage des résultats
    Put_Line("Result1 (sans gestion correcte de la priorité) : " & Integer'Image(result1));
    Put_Line("Result2 (avec gestion correcte de la priorité) : " & Integer'Image(result2));
end TestPriority;
