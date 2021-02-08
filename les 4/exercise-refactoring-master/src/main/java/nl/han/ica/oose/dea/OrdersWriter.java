package nl.han.ica.oose.dea;

public class OrdersWriter {
    private Orders orders;

    public OrdersWriter(Orders orders) {
        this.orders = orders;
    }

    public String getContents() {
        StringBuffer sb = new StringBuffer("{\"orders\": [");

        for (int i = 0; i < orders.getOrdersCount(); i++) {
            Order order = orders.getOrder(i);
            sb.append("{\"id\": ");
            sb.append(order.getOrderId());
            sb.append(", \"products\": [");
            for (int j = 0; j < order.getProductsCount(); j++) {
                Product product = order.getProduct(j);

                sb.append("{\"code\": \"");
                sb.append(product.getCode());
                sb.append("\", \"color\": \"");
                sb.append(product.getColorString());
                sb.append("\", ");

                if (product.getSize() != -1) {
                    sb.append("\"size\": \"");
                    sb.append(product.getSizeString());
                    sb.append("\", ");
                }

                sb.append("\"price\": ");
                sb.append(product.getPrice());
                sb.append(", \"currency\": \"");
                sb.append(product.getCurrency());
                sb.append("\"}, ");
            }

            if (order.getProductsCount() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }

            sb.append("]}, ");
        }

        if (orders.getOrdersCount() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.append("]}").toString();
    }
}
