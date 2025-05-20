function togglePasswordChange() {
    const passwordSection = document.getElementById('passwordSection');
    const changePasswordCheckbox = document.getElementById('changePassword');
    passwordSection.style.display = changePasswordCheckbox.checked ? 'block' : 'none';
}

function toggleBreedField() {
    const species = document.querySelector('input[name="species"]:checked').value;
    const breedField = document.getElementById('breedField');

    if (species === 'собака') {
        breedField.style.display = 'block';
    } else {
        breedField.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const dateInput = document.querySelector('input[type="date"]');
    if (dateInput) {
        const today = new Date().toISOString().split('T')[0];
        dateInput.setAttribute('max', today);
    }
});
