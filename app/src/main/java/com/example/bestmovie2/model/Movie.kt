package com.example.bestmovie2.model

import android.os.Parcelable
import com.example.bestmovie2.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val title: Title = getDefaultTitle(),
    val about: String = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
    val like: Boolean = true
): Parcelable

@Parcelize
data class Title(
    val name: String = "Начало",
    val rating: Int = 0,
    val year: String = "0",
    val image: Int = R.drawable.inception
): Parcelable

fun getDefaultTitle() = Title("Начало", 10, "2010", R.drawable.inception)

fun getWorldMovies(): List<Movie> {
    return listOf(
        Movie(Title("Интерстеллар", 10, "2014", R.drawable.interst), "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.", true),
        Movie(Title("Крестный отец", 8, "1972", R.drawable.godfather), "Криминальная сага, повествующая о нью-йоркской сицилийской мафиозной семье Корлеоне. Фильм охватывает период 1945-1955 годов. Глава семьи, Дон Вито Корлеоне, выдаёт замуж свою дочь. В это время со Второй мировой войны возвращается его любимый сын Майкл. Майкл, герой войны, гордость семьи, не выражает желания заняться жестоким семейным бизнесом. Дон Корлеоне ведёт дела по старым правилам, но наступают иные времена, и появляются люди, желающие изменить сложившиеся порядки. На Дона Корлеоне совершается покушение.", true),
        Movie(Title("Бойцовский клуб", 9, "1999", R.drawable.fight_club), "Сотрудник страховой компании страдает хронической бессонницей и отчаянно пытается вырваться из мучительно скучной жизни. Однажды в очередной командировке он встречает некоего Тайлера Дёрдена — харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а единственное, ради чего стоит жить — саморазрушение.", true),
        Movie(Title("Игры разума", 9, "2001", R.drawable.mind_game), "От всемирной известности до греховных глубин — все это познал на своей шкуре Джон Форбс Нэш-младший. Математический гений, он на заре своей карьеры сделал титаническую работу в области теории игр, которая перевернула этот раздел математики и практически принесла ему международную известность.", true),
        Movie(Title("Довод", 8, "2020", R.drawable.tenet), "После теракта в киевском оперном театре агент ЦРУ объединяется с британской разведкой, чтобы противостоять русскому олигарху, который сколотил состояние на торговле оружием. Для этого агенты используют инверсию времени — технологию будущего, позволяющую времени идти вспять.", true),
        Movie(Title("Марли и я", 7, "2008", R.drawable.marli), "Молодой журналист со своей женой переезжает в другой город на новое место работы. У них грандиозные планы: покупка дома, дети... Но сначала они заводят собаку. Кто бы мог подумать, что именно она станет главным испытанием в их жизни.", false),
        Movie(Title("Зеленая книга", 8, "2018", R.drawable.green_book), "1960-е годы. После закрытия нью-йоркского ночного клуба на ремонт вышибала Тони по прозвищу Болтун ищет подработку на пару месяцев. Как раз в это время Дон Ширли — утонченный светский лев, богатый и талантливый чернокожий музыкант, исполняющий классическую музыку — собирается в турне по южным штатам, где ещё сильны расистские убеждения и царит сегрегация. Он нанимает Тони в качестве водителя, телохранителя и человека, способного решать текущие проблемы. У этих двоих так мало общего, и эта поездка навсегда изменит жизнь обоих.", true),
        Movie(Title("Матрица", 10, "1999", R.drawable.matrix), "Жизнь Томаса Андерсона разделена на две части: днём он — самый обычный офисный работник, получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео, и нет места в сети, куда он бы не смог проникнуть. Но однажды всё меняется. Томас узнаёт ужасающую правду о реальности.", true)
    )
}

fun getRussianMovies(): List<Movie> {
    return listOf(
        Movie(Title("Елки", 3, "2010", R.drawable.tree), "Таксист, лыжник и пенсионерка ждут чуда в канун Нового года. Начало праздничной франшизы Тимура Бекмамбетова", false),
        Movie(Title("Елки", 3, "2010", R.drawable.tree), "Таксист, лыжник и пенсионерка ждут чуда в канун Нового года. Начало праздничной франшизы Тимура Бекмамбетова", false),
        Movie(Title("Елки", 3, "2010", R.drawable.tree), "Таксист, лыжник и пенсионерка ждут чуда в канун Нового года. Начало праздничной франшизы Тимура Бекмамбетова", false),
        Movie(Title("Елки", 3, "2010", R.drawable.tree), "Таксист, лыжник и пенсионерка ждут чуда в канун Нового года. Начало праздничной франшизы Тимура Бекмамбетова", false)
    )
}

