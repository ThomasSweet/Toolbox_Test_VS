package de.sb.toolbox.net;

import static de.sb.toolbox.Asserts.assertThrows;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;
import de.sb.toolbox.net.InetAddresses;


public class InetAddressesTest {
	static private final String MACHINE_NAME = "tabernakel";

	@Test
	public void testLocalInterfacesAndAddresses () {
		final Collection<NetworkInterface> physicalNetworkInterfaces = InetAddresses.physicalNetworkInterfaces();
		final Collection<NetworkInterface> allNetworkInterfaces = InetAddresses.physicalNetworkInterfaces();
		final Collection<InetAddress> localAddresses = InetAddresses.localAddresses();
		final InetAddress localAddress = InetAddresses.localAddress();
		assertFalse(physicalNetworkInterfaces.isEmpty());
		assertFalse(allNetworkInterfaces.isEmpty());
		assertTrue(localAddresses.contains(InetAddresses.LOOPBACK_INET4_ADDRESS) | localAddresses.contains(InetAddresses.LOOPBACK_INET6_ADDRESS));

		for (final NetworkInterface networkInterface : physicalNetworkInterfaces) {
			System.out.format("index=%s, name=%s\n", networkInterface.getIndex(), networkInterface.getName());
		}
		System.out.println("----");
		for (final NetworkInterface networkInterface : allNetworkInterfaces) {
			System.out.format("index=%s, name=%s\n", networkInterface.getIndex(), networkInterface.getName());
		}
		System.out.println("----");
		for (final InetAddress address : localAddresses) {
			System.out.format("address=%s, name=%s, uc=%s, loop=%s\n", address.toString(), address.getHostName(), !address.isMulticastAddress(), address.isLoopbackAddress());
		}
		System.out.println("----");
		System.out.format("address=%s, name=%s, uc=%s, loop=%s\n", localAddress.toString(), localAddress.getHostName(), !localAddress.isMulticastAddress(), localAddress.isLoopbackAddress());
		System.out.println("----");
	}


	@Test
	public void testStringToAddress () {
		assertThrows(() -> InetAddresses.toString((InetAddress) null, false), NullPointerException.class);
		assertThrows(() -> InetAddresses.toAddress((String) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toAddress("locohost"), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toAddress("0.0.0.0.0"), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toAddress("0::0::0"), IllegalArgumentException.class);

		final String[] data = {
			"", "0.0.0.0", "0:0:0:0:0:0:0:0", "::", "::0",
			"127.0.0.1", "localhost", "127.0.1.1", MACHINE_NAME, "0:0:0:0:0:0:0:1", "::1", "ip6-localhost",
			"141.45.7.250", "berndcms.htw-berlin.de"
		};

		final String[][] expected = { {
			"0.0.0.0", "0.0.0.0", "0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0",
			"localhost", "localhost", MACHINE_NAME, MACHINE_NAME, "ip6-localhost", "ip6-localhost", "ip6-localhost",
			"berndcms.htw-berlin.de", "berndcms.htw-berlin.de"
		}, {
			"0.0.0.0", "0.0.0.0", "0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0", "0:0:0:0:0:0:0:0",
			"127.0.0.1", "127.0.0.1", "127.0.1.1", "127.0.1.1", "0:0:0:0:0:0:0:1", "0:0:0:0:0:0:0:1", "0:0:0:0:0:0:0:1",
			"141.45.7.250", "141.45.7.250"
		} };

		for (int loop = 0; loop < 2; ++loop) {
			for (int index = 0; index < data.length; ++index) {
				final InetAddress address = InetAddresses.toAddress(data[index]);
				final String actual = InetAddresses.toString(address, (loop & 1) == 0);
				assertEquals(expected[loop][index], actual);
			}
		}
	}


	@Test
	public void testStringToSocketAddress () {
		assertThrows(() -> InetAddresses.toString((InetSocketAddress) null, false), NullPointerException.class);
		assertThrows(() -> InetAddresses.toSocketAddress((String) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toSocketAddress("localhost"), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toSocketAddress("localhost:ff"), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toSocketAddress("localhost:65536"), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toSocketAddress("localhost:-1"), IllegalArgumentException.class);

		final String[] data = {
			"", ":", "80", ":80", "0.0.0.0:80", ":::80", "::0:80", "0:0:0:0:0:0:0:0:80",
			"127.0.0.1:80", "localhost:80", "127.0.1.1:80", MACHINE_NAME + ":80", "0:0:0:0:0:0:0:1:80", "ip6-localhost:80",
			"141.45.7.250:80", "berndcms.htw-berlin.de:80", "locohost:", "locohost:+80", "0::0::0:65535"
		};

		final String[][] expected = { {
			"0", "0", "80", "80", "80", "80", "80", "80",
			"localhost:80", "localhost:80", MACHINE_NAME + ":80", MACHINE_NAME + ":80", "ip6-localhost:80", "ip6-localhost:80",
			"berndcms.htw-berlin.de:80", "berndcms.htw-berlin.de:80", "locohost:0", "locohost:80", "0::0::0:65535"
		}, {
			"0", "0", "80", "80", "80", "80", "80", "80",
			"127.0.0.1:80", "127.0.0.1:80", "127.0.1.1:80", "127.0.1.1:80", "0:0:0:0:0:0:0:1:80", "0:0:0:0:0:0:0:1:80",
			"141.45.7.250:80", "141.45.7.250:80", "locohost:0", "locohost:80", "0::0::0:65535"
		} };

		for (int loop = 0; loop < 2; ++loop) {
			for (int index = 0; index < data.length; ++index) {
				final InetSocketAddress socketAddress = InetAddresses.toSocketAddress(data[index]);
				final String actual = InetAddresses.toString(socketAddress, (loop & 1) == 0);
				assertEquals(expected[loop][index], actual);
			}
		}

	}


	@Test
	public void testIntegerToAddress () {
		assertThrows(() -> InetAddresses.toInteger((InetAddress) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toAddress((BigInteger) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toAddress(BigInteger.ONE.negate()), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toAddress(BigInteger.ONE.shiftLeft(128)), IllegalArgumentException.class);

		for (final InetAddress expected : InetAddresses.localAddresses()) {
			final BigInteger value = InetAddresses.toInteger(expected);
			final InetAddress actual = InetAddresses.toAddress(value);
			assertEquals(expected, actual);
		}

		final BigInteger[] data = {
			BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(0xffff00000000L), BigInteger.valueOf(0xffff7f000001L), BigInteger.valueOf(0xffff7f000101L),
			BigInteger.valueOf(0xffff8d2d07faL), new BigInteger("fe8000000000000052c9e6fa12a95a89", 16), BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE)
		};

		for (final BigInteger expected : data) {
			final InetAddress address = InetAddresses.toAddress(expected);
			final BigInteger actual = InetAddresses.toInteger(address);
			assertEquals(expected, actual);
		}
	}


	@Test
	public void testIntegerToSocketAddress () {
		assertThrows(() -> InetAddresses.toInteger((InetSocketAddress) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toSocketAddress((BigInteger) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toSocketAddress(BigInteger.ONE.negate()), IllegalArgumentException.class);
		assertThrows(() -> InetAddresses.toSocketAddress(BigInteger.ONE.shiftLeft(144)), IllegalArgumentException.class);

		for (final InetAddress address : InetAddresses.localAddresses()) {
			final int port = ThreadLocalRandom.current().nextInt(0x10000);
			final InetSocketAddress[] socketAddresses = {
				new InetSocketAddress(address, port),
				new InetSocketAddress(InetAddresses.toString(address, false), port),
				new InetSocketAddress(InetAddresses.toString(address, true), port)
			};

			for (final InetSocketAddress expected : socketAddresses) {
				final BigInteger value = InetAddresses.toInteger(expected);
				final InetSocketAddress actual = InetAddresses.toSocketAddress(value);
				assertEquals(expected, actual);
			}
		}

		final BigInteger[] data = {
			BigInteger.ZERO, BigInteger.valueOf(0x7f0000010050L), BigInteger.valueOf(0x7f0001010050L), BigInteger.valueOf(0x8d2d07fa0050L),
			new BigInteger("fe8000000000000052c9e6fa12a95a890050", 16), BigInteger.ONE.shiftLeft(144).subtract(BigInteger.ONE)
		};

		for (final BigInteger value : data) {
			final InetSocketAddress socketAddress = InetAddresses.toSocketAddress(value);
			final BigInteger actual = InetAddresses.toInteger(socketAddress);
			assertEquals(value, actual);
		}
	}


	@Test
	public void testBinaryToAddress () {
		assertThrows(() -> InetAddresses.toBinary((InetAddress) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toAddress((byte[]) null), NullPointerException.class);
		for (final int length : new int[] {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32}) {
			assertThrows(() -> InetAddresses.toAddress(new byte[length]), IllegalArgumentException.class);
		}

		for (final InetAddress expected : InetAddresses.localAddresses()) {
			final byte[] bytes = InetAddresses.toBinary(expected);
			final InetAddress actual = InetAddresses.toAddress(bytes);
			assertEquals(expected, actual);
		}

		final byte[][] data = {
			{0,0,0,0}, {127,0,0,1}, {127,0,1,1}, {-115,45,7,-6},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{-2,-128,0,0,0,0,0,0,82,-55,-26,-6,18,-87,90,-119}, {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}	
		};
		for (final byte[] expected : data) {
			final InetAddress address = InetAddresses.toAddress(expected);
			final byte[] actual = InetAddresses.toBinary(address);
			assertArrayEquals(expected, actual);
		}

		final byte[][] mapped = {
			{0,0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,-1,-1,127,0,0,1},
			{0,0,0,0,0,0,0,0,0,0,-1,-1,127,0,1,1}, {0,0,0,0,0,0,0,0,0,0,-1,-1,-115,45,7,-6},
		};
		for (int index = 0; index < mapped.length; ++index) {
			final InetAddress address = InetAddresses.toAddress(mapped[index]);
			final byte[] actual = InetAddresses.toBinary(address);
			assertArrayEquals(data[index], actual);
		}
	}


	@Test
	public void testBinaryToSocketAddress () {
		assertThrows(() -> InetAddresses.toBinary((InetSocketAddress) null), NullPointerException.class);
		assertThrows(() -> InetAddresses.toSocketAddress((byte[]) null), NullPointerException.class);
		for (final int length : new int[] {0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32}) {
			assertThrows(() -> InetAddresses.toSocketAddress(new byte[length]), IllegalArgumentException.class);
		}

		for (final InetAddress address : InetAddresses.localAddresses()) {
			final int port = ThreadLocalRandom.current().nextInt(0x10000);
			final InetSocketAddress[] socketAddresses = {
				new InetSocketAddress(address, port),
				new InetSocketAddress(InetAddresses.toString(address, false), port),
				new InetSocketAddress(InetAddresses.toString(address, true), port)
			};

			for (final InetSocketAddress expected : socketAddresses) {
				final byte[] bytes = InetAddresses.toBinary(expected);
				final InetSocketAddress actual = InetAddresses.toSocketAddress(bytes);
				assertEquals(expected, actual);
			}
		}

		final byte[][] data = {
			{0,0,0,0,0,0}, {127,0,0,1,0,80}, {127,0,1,1,0,80}, {-115,45,7,-6,0,80},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,80}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{-2,-128,0,0,0,0,0,0,82,-55,-26,-6,18,-87,90,-119,0,80}, {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
		};
		for (final byte[] expected : data) {
			final InetSocketAddress socketAddress = InetAddresses.toSocketAddress(expected);
			final byte[] actual = InetAddresses.toBinary(socketAddress);
			assertArrayEquals(expected, actual);
		}

		final byte[][] mapped = {
			{0,0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,-1,-1,127,0,0,1,0,80},
			{0,0,0,0,0,0,0,0,0,0,-1,-1,127,0,1,1,0,80}, {0,0,0,0,0,0,0,0,0,0,-1,-1,-115,45,7,-6,0,80},
		};
		for (int index = 0; index < mapped.length; ++index) {
			final InetSocketAddress socketAddress = InetAddresses.toSocketAddress(mapped[index]);
			final byte[] actual = InetAddresses.toBinary(socketAddress);
			assertArrayEquals(data[index], actual);
		}

	}
}