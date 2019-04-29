# TICKETNOW


### Ghi chú:
* file cloud_config.properties là file chứa toàn bộ các thông tin về APIs trên Server
AppManager chỉ được khởi tạo 1 lần duy nhất (xem thêm Singleton Design Pattern)
* toàn bộ hình ảnh được load bằng thư viện Picasso
* toàn bộ các list **PHẢI** sử dụng RecyclerView (vì nó tối ưu hóa, xem thêm https://stackoverflow.com/questions/26570325/what-are-recyclerview-advantages-compared-to-listview). Tuy việc implement nó phức tạp hơn ListView nhưng về performance thì nó lại tốt hơn ListView
* Vui lòng tái sử dụng càng nhiều càng tốt, tránh duplicate code

### Flow xử lý API:
1. Khi khởi chạy ứng dụng, AppManager, CommunicationService sẽ được khởi tạo và file cloud_config.properties sẽ được load sẵn
2. Khi thực hiện gọi API, ứng dụng sẽ sử dụng thư viện Volley để thực hiện request
  Tất cả các luồng xử lý doInBackground, onPreExecute, ... đều được Volley xử lý và được định nghĩa trong file Requester
  Khi có kết quả trả về, DataParser sẽ làm nhiệm vụ parse toàn bộ JSON result và map qua object tương ứng
3. Các tầng gọi APIs như Activities, Fragment, không cần quan tâm bên dưới xử lý những gì, tất cả các thông tin nhận thành công, thất bại, .. đều được trả về thông qua Interface DataResponseListener
4. Các tham số chính của 1 request là TAG (dùng để cancel hoặc gọi request thực hiện lại), id (optional, dành cho việc gọi các API có chứa params) và DataResponseListener với kiểu dữ liệu muốn parse


```java
private void getAllLatestMovies() {
        AppManager.getInstance().getCommService().getLatestMovies(TAG_LATEST_MOVIES,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
            @Override
            public void onDataResponse(LinkedList<Movie> result) {
                latestMovies = result;
            }

            @Override
            public void onDataError(String errorMessage) {

            }

            @Override
            public void onRequestError(String errorMessage, VolleyError volleyError) {

            }

            @Override
            public void onCancel() {

            }
        });

    }
```

### Các màn hình đã hoàn thành
* Màn hình chủ (90%) - cho cả xem phim và sự kiện thể thao
* Màn hình thông tin sự kiện (60%) - cho cả xem phim và sự kiện thể thao
* Màn hình mua vé (90%) - cho cả xem phim và sự kiện thể thao
* Màn hình đặt ghế (90%) - cho cả xem phim và sự kiện thể thao
* Màn hình đăng nhập (100%)
* Màn hình đang ký (100%)
* Màn hình thông tin tài khoản (100%)
* Màn hình cập nhật thông tin cá nhân (100%)
* Màn hình đổi mật khẩu (100%)
* Màn hình lịch sử các vé đã mua (100%)

### Các thư viện sử dụng trong ứng dụng
* Thư viện load hình ảnh (từ resource hoặc từ url): https://square.github.io/picasso/
* Thư viện quản lý và thực hiện gọi Request: https://www.journaldev.com/17198/android-volley-tutorial
* Bottom bar: https://github.com/roughike/BottomBar
* Shimmer loading: https://github.com/sharish/ShimmerRecyclerView
* RecyclerView: https://www.androidhive.info/2016/01/android-working-with-recycler-view/
* MovieSeatView: https://github.com/captain-miao/SelectMovieSeat
* TicketView: https://github.com/vipulasri/TicketView
* QRGen: https://github.com/kenglxn/QRGen
