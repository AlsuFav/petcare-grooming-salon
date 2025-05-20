document.addEventListener('DOMContentLoaded', function () {
    const sliders = [
        {
            container: document.getElementById('sliderVertical'),
            captions: document.getElementById('captionsVertical')
        },
        {
            container: document.getElementById('sliderHorizontal'),
            captions: document.getElementById('captionsHorizontal')
        }
    ];

    sliders.forEach(slider => {
        const slides = slider.container.querySelectorAll('.slide');
        const captions = slider.captions.querySelectorAll('.caption');
        let currentSlide = 0;

        function showSlide(index) {
            slides.forEach((slide, i) => {
                slide.classList.remove('active'); // Убираем класс active
                captions[i].style.display = 'none'; // Прячем подпись
            });

            slides[index].classList.add('active'); // Показываем активный слайд
            captions[index].style.display = 'block'; // Показываем подпись
        }

        // Показываем первый слайд при загрузке
        showSlide(currentSlide);

        // Автоматическая смена слайдов каждые 3 секунды
        setInterval(function () {
            currentSlide = (currentSlide + 1) % slides.length; // Следующий слайд
            showSlide(currentSlide);
        }, 3000);
    });
});

function toggleAnswer(id) {
    const answer = document.getElementById(id);
    if (answer.style.display === 'block') {
        answer.style.display = 'none';
    } else {
        answer.style.display = 'block';
    }
}