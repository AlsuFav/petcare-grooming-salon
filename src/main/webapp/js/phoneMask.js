document.addEventListener("DOMContentLoaded", function () {
    const phoneInput = document.querySelectorAll('input[type="tel"]');
    if (typeof Inputmask !== 'undefined') {
        phoneInput.forEach(input => {
            Inputmask("+7 (999) 999-99-99").mask(input);
        });
    } else {
        console.warn("Inputmask is not loaded. Phone mask will not be applied.");
    }
});
