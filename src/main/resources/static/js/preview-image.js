document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('photo');
    const previewImg = document.getElementById('preview');
    console.log("Le fichier preview-image.js est bien chargé !");
    fileInput.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (file && file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImg.src = e.target.result;
                previewImg.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    });
});