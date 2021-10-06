
public class TestIcon {
	private static Icon icon1, icon2;
	
	public static void main(String[] args) {
		// Tests with few data items

		Pixel pixels[] = new Pixel[5];
		Position points[] = new Position[5];
		int colors[] = {5, 3, 1, 2, 4};
		int x[] = {4,2,1,1,3};
		int y[] = {4,3,1,2,4};

		for (int i = 0; i < 5; ++i) {
			points[i] = new Position(x[i],y[i]);
			pixels[i] = new Pixel(points[i],colors[i]);
		}

		
		try {
			
			Position offset1 = new Position(3, 4);
			Position offset2 = new Position(6, 8);
			
			icon1 = new Icon(1, 3, 4, "Still", offset1);
			icon2 = new Icon(2, 3, 4, "Still", offset2);
			
			for (int i = 0; i < 5; i++) {
				icon1.addPixel(pixels[i]);
				icon2.addPixel(pixels[i]);
			}
			icon2.addPixel(new Pixel(new Position(0, 0), 3));
//			icon1.bst.put(icon1.bst.getRoot(), new Pixel(new Position(-3, -4), 3));
			if (icon1.intersects(icon2)) {
				System.out.println("Test 1 passed.");
			}else {
				System.out.println("Test 1 failed");
			}
			
			icon1 = new Icon(1, 3, 4, "Still", offset1);
			icon2 = new Icon(1, 1, 1, "Still", offset1);
			icon2.addPixel(new Pixel(new Position(0, 0), 3));
			icon1.addPixel(new Pixel(new Position(0, 0), 1));
			
			if (icon1.intersects(icon2)) {
				System.out.println("Test 2 passed.");
			} 
			else {
				System.out.println("Test 2 failed.");
			}
			
			icon1 = new Icon(1, 3, 4, "Still", offset1);
			icon2 = new Icon(1, 1, 1, "Still", offset1);
			icon2.addPixel(new Pixel(new Position(0, 0), 3));
			icon1.addPixel(new Pixel(new Position(2, 0), 1));
			if (!icon1.intersects(icon2)) {
				System.out.println("Test 3 passed.");
			} 
			else {
				System.out.println("Test 2 failed.");
			}
			
		}
		catch(Exception e) {
			System.out.println("Tests failed");
		}

	}
}
