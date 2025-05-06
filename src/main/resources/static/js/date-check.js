document.addEventListener("DOMContentLoaded", function () {
    const debutInput = document.getElementById("debut");
    const finInput = document.getElementById("fin");

    // Fonction pour formater une date au format 'YYYY-MM-DDTHH:MM'
    function formatDateToDatetimeLocal(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }

    // Fonction pour ajouter des heures à une date
    function addHours(dateString, hoursToAdd) {
        const date = new Date(dateString);
        date.setHours(date.getHours() + hoursToAdd);
        return date;
    }

    if (debutInput && finInput) {
        // Initialisation des valeurs par défaut au chargement
        const now = new Date();
        debutInput.value = formatDateToDatetimeLocal(now);
        debutInput.min = debutInput.value;

        const finPlus4h = addHours(debutInput.value, 4);
        finInput.value = formatDateToDatetimeLocal(finPlus4h);
        finInput.min = debutInput.value;

        // Mettre à jour fin automatiquement quand debut change
        debutInput.addEventListener("change", function () {
            const selectedStart = debutInput.value;
            const newFinDate = addHours(selectedStart, 4);
            finInput.value = formatDateToDatetimeLocal(newFinDate);
            finInput.min = selectedStart;
        });
    }
});
