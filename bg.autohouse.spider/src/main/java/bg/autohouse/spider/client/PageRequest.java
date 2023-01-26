package bg.autohouse.spider.client;

public class PageRequest implements Pagination {
  public static final int DEFAULT_SIZE = 35;

  private final int page;
  private final int size;

  private PageRequest(int page, int size) {
    this.page = page;
    this.size = size;
  }

  public static PageRequest of(int page, int size) {
    return new PageRequest(page, size);
  }

  public static PageRequest of(int page) {
    return new PageRequest(page, DEFAULT_SIZE);
  }

  public static PageRequest ofSize(int pageSize) {
    return PageRequest.of(0, pageSize);
  }

  @Override
  public int pageSize() {
    return this.size;
  }

  @Override
  public int pageNumber() {
    return this.page;
  }

  @Override
  public int offset() {
    return page * size;
  }

  @Override
  public PageRequest next() {
    return PageRequest.of(this.pageNumber() + 1, this.pageSize());
  }

  @Override
  public PageRequest previous() {
    return this.pageNumber() == 0 ? this : PageRequest.of(this.pageNumber() - 1, this.pageSize());
  }

  @Override
  public PageRequest first() {
    return PageRequest.of(0, this.pageSize());
  }
}
