package bg.autohouse.spider.client;

public interface Pagination
{
    int pageSize();

    int pageNumber();

    int offset();

    PageRequest next();

    PageRequest previous();

    PageRequest first();
}
